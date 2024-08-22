@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMotionApi::class,
    ExperimentalPermissionsApi::class,
)

package uz.mobile.footzone.android.presentation.screens.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.IconImage
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.extension.compose.style.MapStyle
import org.koin.androidx.compose.koinViewModel
import uz.mobile.footzone.android.R
import uz.mobile.footzone.android.common.bitmapFromDrawableRes
import uz.mobile.footzone.android.common.swipeUpDown
import uz.mobile.footzone.android.presentation.components.AppCircleButton
import uz.mobile.footzone.android.presentation.components.StadiumListItem
import uz.mobile.footzone.android.presentation.screens.app_sheets.HomeScreenActionBottomSheet
import uz.mobile.footzone.android.presentation.screens.app_sheets.MainScreenTopSheet
import uz.mobile.footzone.android.presentation.screens.app_sheets.StadiumListBottomSheet
import uz.mobile.footzone.android.presentation.utils.shareStadiumLocationToNavigators
import uz.mobile.footzone.android.theme.neutral100
import uz.mobile.footzone.android.theme.neutral15
import uz.mobile.footzone.android.theme.neutral40
import uz.mobile.footzone.android.theme.neutral80
import uz.mobile.footzone.android.theme.neutral90
import uz.mobile.footzone.android.theme.shadowBottomSheet
import uz.mobile.footzone.android.theme.shadowStadiumItem
import uz.mobile.footzone.domain.model.StadiumUiModel
import uz.mobile.footzone.domain.model.UserType
import uz.mobile.footzone.presentation.main.BottomSheetAction
import uz.mobile.footzone.presentation.main.Location
import uz.mobile.footzone.presentation.main.MainScreenSideEffects
import uz.mobile.footzone.presentation.main.MainScreenUiEvent
import uz.mobile.footzone.presentation.main.MainState
import uz.mobile.footzone.presentation.main.SheetState
import uz.mobile.footzone.presentation.main.UserLocation

@Composable
fun MainScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel(),
    context: Context = LocalContext.current,
    onNavigateNotifications: () -> Unit,
    onNavigateOwnerStadiums: () -> Unit,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val sideEffects by viewModel.sideEffect.collectAsStateWithLifecycle(MainScreenSideEffects.Nothing)

    val locationPermissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION,
        onPermissionResult = viewModel::onLocationPermissionResult
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.setPermissionState(locationPermissionState)
    }

    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            center(Point.fromLngLat(69.2401, 41.2995))
            zoom(14.0)
        }
    }

    val resultLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d("TAG", "MainScreenRoute: $result")
            viewModel.onLocationPermissionResult()
        }

    MainScreen(
        modifier = modifier.fillMaxSize(),
        state = state,
        context = context,
        mapViewportState = mapViewportState,
        navigateToNotifications = {
            viewModel.onUiEvent(MainScreenUiEvent.Notification)
        },
        locateUser = {
            viewModel.onUiEvent(MainScreenUiEvent.RequestLocationPermission)
        },
        onSheetStateChange = {
            viewModel.onUiEvent(MainScreenUiEvent.BottomSheetStateChange(it))
        },
        onListSheetStateChange = {
            viewModel.onUiEvent(MainScreenUiEvent.ListBottomSheetStateChange(it))
        },
        onActionPerformed = {
            viewModel.onUiEvent(MainScreenUiEvent.BottomSheetActionPerformed(it))
        },
        onSearchValueChanged = {
            viewModel.onUiEvent(MainScreenUiEvent.SearchQueryChanged(it))
        },
        performSearch = {
            viewModel.onUiEvent(MainScreenUiEvent.PerformSearch)
        },
        onPinClick = {
            viewModel.onUiEvent(MainScreenUiEvent.OnStadiumSelected(it))
        },
        onBackPressed = {
            viewModel.onUiEvent(MainScreenUiEvent.OnBackPressed)
        },
        onCurrentlyOpenSelected = {
            viewModel.onUiEvent(MainScreenUiEvent.CurrentlyOpenFilter)
        },
        onWellRatedSelected = {
            viewModel.onUiEvent(MainScreenUiEvent.WellRatedFilter)
        },
        onAddStadiumBookmark = {
            viewModel.onUiEvent(MainScreenUiEvent.SaveStadium(it))
        },
        onBookStadium = {
            viewModel.onUiEvent(MainScreenUiEvent.BookStadium(it))
        },
        onNavigateToStadium = {
            viewModel.onUiEvent(MainScreenUiEvent.NavigateToStadium(it))
        }
    )

    when (sideEffects) {
        MainScreenSideEffects.NavigateToNotifications -> {
            onNavigateNotifications()
        }

        MainScreenSideEffects.NavigateToOwnerStadiums -> {
            onNavigateOwnerStadiums()
        }

        is MainScreenSideEffects.OpenNavigatorChoose -> {
            LaunchedEffect(sideEffects) {
                val location =
                    (sideEffects as MainScreenSideEffects.OpenNavigatorChoose).stadiumLocation
                context.shareStadiumLocationToNavigators(location)
            }
        }

        MainScreenSideEffects.OpenGPSettings -> {
            LaunchedEffect(sideEffects) {
                resultLauncher.launch(
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                )
            }
        }

        MainScreenSideEffects.Nothing -> {}
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    context: Context,
    state: MainState,
    mapViewportState: MapViewportState,
    navigateToNotifications: () -> Unit,
    locateUser: () -> Unit,
    onSheetStateChange: (SheetState) -> Unit,
    onListSheetStateChange: (SheetState) -> Unit,
    onActionPerformed: (BottomSheetAction) -> Unit,
    onSearchValueChanged: (String) -> Unit,
    performSearch: () -> Unit,
    onPinClick: (StadiumUiModel) -> Unit,
    onCurrentlyOpenSelected: () -> Unit,
    onWellRatedSelected: () -> Unit,
    onBackPressed: () -> Unit,
    onAddStadiumBookmark: (StadiumUiModel) -> Unit,
    onBookStadium: (StadiumUiModel) -> Unit,
    onNavigateToStadium: (StadiumUiModel) -> Unit,
) {
    MapView(
        modifier = modifier,
        mapViewportState = mapViewportState,
        stadiums = state.stadiums,
        selectedStadium = state.selectedStadium,
        userLocation = state.userLocation,
        onPinClick = onPinClick
    )

    Box(modifier = modifier) {
        InitialHomeAction(
            modifier = Modifier.fillMaxSize(),
            context = context,
            sheetState = state.sheetState,
            userLocation = state.userLocation,
            searchQuery = state.searchQuery,
            isOwner = state.userType == UserType.STADIUM_OWNER,
            navigateToNotifications = navigateToNotifications,
            locateUser = locateUser,
            onSheetStateChange = onSheetStateChange,
            onActionPerformed = onActionPerformed,
            onSearchValueChanged = onSearchValueChanged,
            performSearch = performSearch
        )

        StadiumDataDisplay(
            modifier = Modifier.fillMaxSize(),
            context = context,
            stadiums = state.filteredStadiums,
            sheetState = state.listSheetState,
            selectedStadium = state.selectedStadium,
            onBackPressed = onBackPressed,
            onListSheetStateChange = onListSheetStateChange,
            onCurrentlyOpenSelected = onCurrentlyOpenSelected,
            onWellRatedSelected = onWellRatedSelected,
            currentlyOpenFilterActive = state.currentlyOpenFilterActive,
            wellRatedFilterActive = state.wellRatedFilterActive,
            onAddStadiumBookmark = onAddStadiumBookmark,
            onBookStadium = onBookStadium,
            onNavigateToStadium = onNavigateToStadium
        )

        state.selectedStadium?.let {
            StadiumListItem(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                    .clip(MaterialTheme.shapes.large),
                stadium = it,
                onAddStadiumBookmark = onAddStadiumBookmark,
                onBookStadium = onBookStadium,
                onNavigateToStadium = onNavigateToStadium
            )
        }
    }
}

@Composable
fun InitialHomeAction(
    modifier: Modifier = Modifier,
    context: Context,
    sheetState: SheetState,
    userLocation: UserLocation,
    searchQuery: String,
    isOwner: Boolean,
    navigateToNotifications: () -> Unit,
    locateUser: () -> Unit,
    onSheetStateChange: (SheetState) -> Unit,
    onActionPerformed: (BottomSheetAction) -> Unit,
    onSearchValueChanged: (String) -> Unit,
    performSearch: () -> Unit,
) {
    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.motion_scene)
            .readBytes()
            .decodeToString()
    }

    val progress by animateFloatAsState(
        targetValue = sheetState.value,
        animationSpec = tween(durationMillis = 300),
        label = stringResource(R.string.bottom_sheet_expansion)
    )

    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = progress,
        modifier = modifier
    ) {

        HomeTop(
            modifier = Modifier
                .layoutId(stringResource(id = R.string.home_top_address)),
            userAddress = userLocation,
            onNavigateNotifications = navigateToNotifications,
        )

        AppCircleButton(
            modifier = Modifier
                .layoutId(stringResource(R.string.user_locator_id)),
            icon = R.drawable.my_location,
            onClick = locateUser
        )

        HomeScreenActionBottomSheet(
            modifier = Modifier
                .layoutId(stringResource(R.string.bottom_sheet_id))
                .swipeUpDown(
                    onScrollDown = {
                        onSheetStateChange(SheetState.HalfExpanded)
                    },
                    onScrollUp = {
                        onSheetStateChange(SheetState.FullExpanded)
                    }
                ),
            query = searchQuery,
            onActionPerformed = onActionPerformed,
            onSearchValueChanged = onSearchValueChanged,
            performSearch = performSearch,
            isUserStadiumOwner = isOwner
        )
    }
}

@Composable
fun StadiumDataDisplay(
    modifier: Modifier = Modifier,
    context: Context,
    sheetState: SheetState,
    stadiums: List<StadiumUiModel>,
    selectedStadium: StadiumUiModel?,
    currentlyOpenFilterActive: Boolean,
    wellRatedFilterActive: Boolean,
    onBackPressed: () -> Unit,
    onCurrentlyOpenSelected: () -> Unit,
    onWellRatedSelected: () -> Unit,
    onListSheetStateChange: (SheetState) -> Unit,
    onAddStadiumBookmark: (StadiumUiModel) -> Unit,
    onBookStadium: (StadiumUiModel) -> Unit,
    onNavigateToStadium: (StadiumUiModel) -> Unit,
) {

    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.motion_scene_data)
            .readBytes()
            .decodeToString()
    }

    val progress by animateFloatAsState(
        targetValue = sheetState.value,
        animationSpec = tween(durationMillis = 300),
        label = stringResource(R.string.bottom_sheet_expansion)
    )

    val cornerRadius by animateIntAsState(
        targetValue = if (sheetState == SheetState.FullExpanded) 0 else 16,
        label = "sheet_corner"
    )

    val shadow by animateColorAsState(
        targetValue = if (sheetState == SheetState.FullExpanded) Color.White else shadowBottomSheet,
        label = "sheet_shadow_color"
    )

    val shadowTop by animateColorAsState(
        targetValue = if (sheetState == SheetState.FullExpanded) Color.White else shadowStadiumItem,
        label = "sheet_shadow_color"
    )

    Box(modifier = modifier) {
        MotionLayout(
            motionScene = MotionScene(content = motionScene),
            //topBar + buttonHeight + padding + radius + handler ==>> 64+32+16+8+4=124
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 124.dp),
            progress = progress
        ) {
            StadiumListBottomSheet(
                modifier = Modifier
                    .layoutId(stringResource(id = R.string.bottom_sheet_list_id))
                    .swipeUpDown(
                        onScrollDown = {
                            onListSheetStateChange(SheetState.PartiallyExpanded)
                        },
                        onScrollUp = {
                            onListSheetStateChange(
                                SheetState.FullExpanded
                            )
                        }
                    ),
                shadowColor = shadow,
                cornerRadius = cornerRadius.dp,
                stadiums = stadiums,
                onAddStadiumBookmark = onAddStadiumBookmark,
                onBookStadium = onBookStadium,
                onNavigateToStadium = onNavigateToStadium
            )
        }

        if (sheetState != SheetState.Hidden || selectedStadium != null) {
            MainScreenTopSheet(
                onBack = onBackPressed,
                shadowColor = shadowTop,
                currentlyOpenFilterActive = currentlyOpenFilterActive,
                wellRatedFilterActive = wellRatedFilterActive,
                onCurrentlyOpenSelected = onCurrentlyOpenSelected,
                onWellRatedSelected = onWellRatedSelected
            )
        }
    }
}

@Composable
fun HomeTop(
    modifier: Modifier,
    userAddress: UserLocation,
    onNavigateNotifications: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(top = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(start = 48.dp)
        ) {
            Text(
                text = stringResource(R.string.user_address),
                style = MaterialTheme.typography.bodySmall.copy(color = neutral90)
            )
            Text(
                text = userAddress.userAddress.plus(" ${userAddress.point?.longitude} ${userAddress.point?.latitude}"),
                style = MaterialTheme.typography.labelLarge.copy(color = neutral100)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        AppCircleButton(icon = R.drawable.notifications, onClick = onNavigateNotifications)
    }
}


@Composable
fun SearchInputField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    performSearch: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val enabled = true

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        interactionSource = interactionSource,
        modifier = modifier
            .fillMaxWidth(),
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = neutral100),
        keyboardOptions = KeyboardOptions().copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            performSearch()
        })
    ) {
        OutlinedTextFieldDefaults.DecorationBox(
            value = value,
            visualTransformation = VisualTransformation.None,
            innerTextField = it,
            singleLine = true,
            enabled = enabled,
            interactionSource = interactionSource,
            // keep vertical paddings but change the horizontal
            contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                start = 16.dp, end = 19.dp
            ),
            colors = OutlinedTextFieldDefaults.colors().copy(
                focusedIndicatorColor = neutral40,
                unfocusedIndicatorColor = neutral40,
                focusedTextColor = neutral100,
            ),
            placeholder = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.search),
                        contentDescription = null
                    )
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyLarge.copy(color = neutral80)
                    )
                }
            },
        )
    }
}

@Composable
fun ActionContainer(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    @StringRes label: Int,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable(onClick = onClick)
            .background(neutral15)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = icon),
            contentDescription = stringResource(id = label)
        )

        Text(
            text = stringResource(id = label),
            style = MaterialTheme.typography.labelMedium.copy(color = neutral100)
        )
    }
}

@Composable
fun MapView(
    modifier: Modifier = Modifier,
    mapViewportState: MapViewportState,
    stadiums: List<StadiumUiModel>,
    selectedStadium: StadiumUiModel?,
    userLocation: UserLocation,
    onPinClick: (StadiumUiModel) -> Unit
) {
    val context = LocalContext.current
    MapboxMap(
        modifier,
        mapViewportState = mapViewportState,
        style = {
            MapStyle(style = Style.MAPBOX_STREETS)
        },
        scaleBar = {}
    ) {
        userLocation.point?.let { location ->
            UserLiveLocationPin(context = context, location = location)
        }
        stadiums.forEach { stadium ->
            StadiumMarker(
                context = context,
                stadium = stadium,
                isSelected = stadium == selectedStadium,
                onPinClick = onPinClick
            )
        }
    }
}

@Composable
fun UserLiveLocationPin(context: Context, location: Location) {
    bitmapFromDrawableRes(
        context,
        R.drawable.my_location
    )?.let { iconBitmap ->
        PointAnnotation(
            point = Point.fromLngLat(
                location.longitude,
                location.latitude
            )
        ) {
            iconImage = IconImage(bitmap = iconBitmap)
        }
    }
}

@Composable
fun StadiumMarker(
    context: Context,
    stadium: StadiumUiModel,
    isSelected: Boolean = false,
    onPinClick: (StadiumUiModel) -> Unit
) {
    val drawableRes = if (isSelected) R.drawable.pin_selected else R.drawable.pin
    bitmapFromDrawableRes(
        context,
        drawableRes
    )?.let { iconBitmap ->
        PointAnnotation(
            point = Point.fromLngLat(
                stadium.longitude,
                stadium.latitude
            ),
            onClick = {
                onPinClick(stadium)
                true
            }
        ) {
            iconImage = IconImage(bitmap = iconBitmap)
        }
    }
}