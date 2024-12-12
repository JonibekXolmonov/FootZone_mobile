package uz.mobile.footzone.android.presentation.screens.stadium_detail

import android.content.Context
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import uz.mobile.footzone.presentation.stadium_detail.StadiumDetailSideEffects
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import uz.mobile.footzone.android.R
import uz.mobile.footzone.android.presentation.components.AppPrimaryBorderedButton
import uz.mobile.footzone.android.presentation.components.AppPrimaryButton
import uz.mobile.footzone.android.presentation.components.AppTopBar
import uz.mobile.footzone.android.presentation.components.StarRatingBar
import uz.mobile.footzone.android.presentation.screens.dialogs.CalendarAlert
import uz.mobile.footzone.android.presentation.utils.shareStadiumLocationToNavigators
import uz.mobile.footzone.android.theme.green600
import uz.mobile.footzone.android.theme.neutral10
import uz.mobile.footzone.android.theme.neutral100
import uz.mobile.footzone.android.theme.neutral15
import uz.mobile.footzone.android.theme.neutral20
import uz.mobile.footzone.android.theme.neutral30
import uz.mobile.footzone.android.theme.neutral80
import uz.mobile.footzone.android.theme.neutral90
import uz.mobile.footzone.android.theme.yellow50
import uz.mobile.footzone.android.theme.yellow600
import uz.mobile.footzone.domain.model.StadiumUiModel
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collectLatest
import uz.mobile.footzone.android.presentation.components.TimePickerField
import uz.mobile.footzone.presentation.stadium_detail.StadiumDetailState
import uz.mobile.footzone.presentation.stadium_detail.StadiumDetailUiEvent

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StadiumDetailRoute(
    modifier: Modifier = Modifier,
    viewModel: StadiumDetailViewModel = koinViewModel(),
    context: Context = LocalContext.current,
    stadiumId: Int?,
    onBackPressed: () -> Unit,
    onNavigateTimeInterval: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val dialogState = remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current

    StadiumDetailScreen(
        modifier = modifier.fillMaxSize(),
        state = state,
        onBookStadium = {
            viewModel.onUiEvent(StadiumDetailUiEvent.BookStadium(it))
        },
        onNavigateToStadium = {
            viewModel.onUiEvent(StadiumDetailUiEvent.NavigateToStadium(it))
        },
        onAddStadiumBookmark = {
            viewModel.onUiEvent(StadiumDetailUiEvent.SaveStadium(it))
        },
        onShareStadium = {
            viewModel.onUiEvent(StadiumDetailUiEvent.SaveStadium(it))
        },
        onSelectDate = {
            viewModel.onUiEvent(StadiumDetailUiEvent.SelectDate)
        },
        onNavigateTimeInterval = onNavigateTimeInterval,
        onBackPressed = onBackPressed
    )

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.sideEffect.collectLatest {
                when (it) {
                    is StadiumDetailSideEffects.OpenNavigatorChoose -> {
                        val location = it.stadiumLocation
                        context.shareStadiumLocationToNavigators(location)
                    }

                    is StadiumDetailSideEffects.OpenShareChoose -> {
                        // TODO: Implement share functionality
                    }

                    is StadiumDetailSideEffects.OpenCalendarAlert -> {
                        dialogState.value = true
                    }

                    StadiumDetailSideEffects.Nothing -> {
                    }
                }
            }
        }
    }


    if (dialogState.value) {
        CalendarAlert(
            onDismissRequest = {
                dialogState.value = false
            },
            onSelectDate = { selectedDate ->
                dialogState.value = false
            }
        )
    }
}

@OptIn(
    ExperimentalMaterial3Api::class,
)
@Composable
fun StadiumDetailScreen(
    modifier: Modifier = Modifier,
    state: StadiumDetailState,
    onAddStadiumBookmark: (StadiumUiModel) -> Unit,
    onBookStadium: (StadiumUiModel) -> Unit,
    onNavigateToStadium: (StadiumUiModel) -> Unit,
    onShareStadium: (StadiumUiModel) -> Unit,
    onSelectDate: () -> Unit,
    onNavigateTimeInterval: () -> Unit,
    onBackPressed: () -> Unit
) {
    val lazyListState = rememberLazyListState()

    val title by remember {
        derivedStateOf {
            when {
                lazyListState.firstVisibleItemIndex == 0 -> ""
                lazyListState.firstVisibleItemIndex == 1 -> "Acme futbol maydoni"
                lazyListState.firstVisibleItemIndex >= 2 -> "Barcha sharhlar"
                else -> ""
            }
        }
    }

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded,
            skipHiddenState = false
        )
    )
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        topBar = {
            AppTopBar(
                title = title,
                onBack = onBackPressed,
            )
        },
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 0.dp,
        sheetDragHandle = null,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            BookBottomSheetContent(
                onSelectDate = onSelectDate,
                onNavigateTimeInterval = onNavigateTimeInterval,
                onBottomSheetEnabled = {
                    coroutineScope.launch {
                        bottomSheetScaffoldState.bottomSheetState.hide()
                    }
                }
            )
        },
        containerColor = neutral10
    ) { padding ->
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
        ) {
            item { StadiumImages() }
            item {
                StadiumInfo(
                    onBookStadium = {
                        coroutineScope.launch {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
                    },
                    onNavigateToStadium = onNavigateToStadium,
                    onAddStadiumBookmark = onAddStadiumBookmark,
                    onShareStadium = onShareStadium,
                )
            }
            item { StadiumReviews() }
        }
    }
}

@Composable
fun StadiumImages(
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(4) {
            when (it) {
                0 -> StadiumImage(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(width = 240.dp, height = 180.dp)
                )

                3 -> StadiumImage(
                    modifier = Modifier.padding(end = 16.dp)
                )

                else -> StadiumImage()
            }
        }
    }
}

@Composable
fun StadiumImage(
    modifier: Modifier = Modifier,
    @DrawableRes id: Int = R.drawable.stadium
) {
    Image(
        painter = painterResource(id = id),
        contentDescription = null,
        modifier = modifier
            .size(240.dp, 180.dp)
            .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun StadiumInfo(
    onAddStadiumBookmark: (StadiumUiModel) -> Unit,
    onBookStadium: () -> Unit,
    onNavigateToStadium: (StadiumUiModel) -> Unit,
    onShareStadium: (StadiumUiModel) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 20.dp)
    ) {

        Text(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp),
            text = "Acme futbol maydoni",
            style = MaterialTheme.typography.headlineMedium.copy(color = neutral100)
        )

        InfoItem(
            icon = R.drawable.location_on,
            value = "Amir Temur shoh ko’chasi, 14-uy"
        )

        InfoItem(
            icon = R.drawable.phone,
            value = "+998 99 777 66 55"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.access_time),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text(
                    text = "Ochiq",
                    style = MaterialTheme.typography.bodyLarge.copy(color = green600)
                )
                Text(
                    text = "·",
                    style = MaterialTheme.typography.bodyLarge.copy(color = neutral90)
                )
                Text(
                    text = "00:00 da yopiladi",
                    style = MaterialTheme.typography.bodyLarge.copy(color = neutral90)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.expand_more),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }

        InfoItem(
            icon = R.drawable.money,
            value = "50 000 so’m/soat"
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            item {
                AppPrimaryButton(
                    text = stringResource(R.string.book),
                    onClick = {
                        onBookStadium()
                    },
                    leadingIcon = R.drawable.book
                )
            }

            item {
                AppPrimaryBorderedButton(
                    leadingIcon = R.drawable.navigation,
                    text = stringResource(R.string.route),
                    contentPaddingValues = PaddingValues(vertical = 10.dp, horizontal = 24.dp),
                    onClick = {
                        //onNavigateToStadium()
                    }
                )
            }

            item {
                AppPrimaryBorderedButton(
                    leadingIcon = R.drawable.bookmark_add,
                    text = stringResource(R.string.add_bookmark),
                    contentPaddingValues = PaddingValues(vertical = 10.dp, horizontal = 24.dp),
                    onClick = {
                        //onAddStadiumBookmark()
                    }
                )
            }

            item {
                AppPrimaryBorderedButton(
                    leadingIcon = R.drawable.share,
                    text = stringResource(R.string.share),
                    contentPaddingValues = PaddingValues(vertical = 10.dp, horizontal = 24.dp),
                    onClick = {
                        //onShareStadium()
                    }
                )
            }
        }
    }
}

@Composable
fun InfoItem(
    icon: Int,
    value: String,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable(
                onClick = { onClick?.invoke() },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(color = neutral90)
        )
    }
}

@Composable
fun StadiumReviews() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(neutral20)
        )

        Text(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp),
            text = stringResource(R.string.ratings_reviews),
            style = MaterialTheme.typography.titleMedium.copy(color = neutral100)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Column(
                modifier = Modifier.width(88.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "4,2",
                        style = MaterialTheme.typography.displayMedium.copy(color = neutral100)
                    )
                }

                StarRatingBar(rating = 3f) {
                    //TODO
                }

                Text(
                    text = "24 ta ovoz",
                    style = MaterialTheme.typography.bodySmall.copy(color = neutral80)
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                RatingItem(value = 5, percent = 100)
                RatingItem(value = 4, percent = 40)
                RatingItem(value = 3, percent = 0)
                RatingItem(value = 2, percent = 0)
                RatingItem(value = 1, percent = 0)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            repeat(5) {
                ReviewItem()
            }
        }
    }
}

@Composable
fun RatingItem(value: Int, percent: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "$value",
            style = MaterialTheme.typography.bodySmall.copy(color = neutral80)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(yellow50, RoundedCornerShape(4.dp))
        ) {
            if (percent == 0) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .align(Alignment.CenterStart)
                        .background(yellow600, CircleShape)
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(percent / 100f)
                        .background(yellow600, RoundedCornerShape(12.dp))
                )
            }
        }
    }
}

@Composable
fun ReviewItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(neutral15, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user_image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                )

                Text(
                    text = "Azizjon Akramov",
                    style = MaterialTheme.typography.labelLarge.copy(color = neutral100)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                StarRatingBar(rating = 3f) {
                    //TODO
                }

                Text(
                    text = "17.04.2022",
                    style = MaterialTheme.typography.bodyMedium.copy(color = neutral80)
                )
            }

            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Odio urna sagittis, elit, molestie dui in blandit cursus. Venenatis pellentesque viverra auctor odio commodo, iaculis sit senectus. Lorem sollicitudin pretium et sollicitudin vitae aliquet.",
                style = MaterialTheme.typography.bodyMedium.copy(color = neutral90)
            )
        }
    }
}

@Composable
fun BookBottomSheetContent(
    onBottomSheetEnabled: () -> Unit,
    onSelectDate: () -> Unit,
    onNavigateTimeInterval: () -> Unit,
) {
    val selectedDate by remember { mutableStateOf("13-may, 2022") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(neutral10)
            .padding(top = 20.dp, bottom = 26.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            modifier = Modifier
                .padding(start = 16.dp, bottom = 8.dp),
            text = stringResource(R.string.book),
            style = MaterialTheme.typography.titleMedium.copy(color = neutral100)
        )

        TimePickerField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            hint = "Sana tanlang",
            value = selectedDate,
            icon = R.drawable.today,
            onClick = onSelectDate
        )

        TimePickerField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            hint = "Vaqt oralig’i",
            value = null,
            icon = R.drawable.trailing_icon,
            onClick = onNavigateTimeInterval
        )

        Spacer(
            modifier = Modifier
                .padding(top = 22.dp, bottom = 16.dp)
                .fillMaxWidth()
                .height(8.dp)
                .background(neutral20)
        )

        InfoItem(
            icon = R.drawable.trailing_icon,
            value = "2 soat",
            onClick = {
                //TODO
            }
        )

        InfoItem(
            icon = R.drawable.money,
            value = "-",
            onClick = {
                //TODO
            }
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            AppPrimaryBorderedButton(
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                text = stringResource(R.string.cancel),
                onClick = onBottomSheetEnabled
            )

            AppPrimaryButton(
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                enabled = false,
                text = stringResource(R.string.book),
                onClick = {
                    //TODO
                },
            )
        }
    }
}
