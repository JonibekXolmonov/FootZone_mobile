package uz.mobile.footzone.android.presentation.screens.account

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import org.koin.androidx.compose.koinViewModel
import uz.mobile.footzone.android.R
import uz.mobile.footzone.android.presentation.components.AppPrimaryBorderedButton
import uz.mobile.footzone.android.presentation.components.AppPrimaryButton
import uz.mobile.footzone.android.theme.neutral10
import uz.mobile.footzone.android.theme.neutral100
import uz.mobile.footzone.android.theme.neutral20
import uz.mobile.footzone.android.theme.neutral90
import uz.mobile.footzone.domain.model.UserType
import uz.mobile.footzone.presentation.account.AccountScreenUiEvent
import uz.mobile.footzone.presentation.account.AccountSideEffects
import uz.mobile.footzone.presentation.account.AccountState
import uz.mobile.footzone.presentation.main.MainScreenSideEffects

@Composable
fun AccountRoute(
    modifier: Modifier = Modifier,
    viewModel: AccountViewModel = koinViewModel(),
    context: Context = LocalContext.current,
    onNavigateToAuth: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToLanguageChange: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val sideEffects by viewModel.sideEffect.collectAsStateWithLifecycle(MainScreenSideEffects.Nothing)

    val resultLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                viewModel.onImagePicked(it)
            }
        }

    AccountScreen(
        modifier = modifier.fillMaxSize(),
        context = context,
        state = state,
        login = {
            viewModel.onUiEvent(AccountScreenUiEvent.Login)
        },
        logOut = {
            viewModel.onUiEvent(AccountScreenUiEvent.LogOut)
        },
        onBack = {

        },
        onChangeUserImage = {
            viewModel.onUiEvent(AccountScreenUiEvent.ChangeUserImage)
        },
        onNavigateToEditName = {
            viewModel.onUiEvent(AccountScreenUiEvent.UserName)
        },
        onNavigateToEditNumber = {
            viewModel.onUiEvent(AccountScreenUiEvent.UserNumber)
        },
        onNavigateToNotifications = {
            viewModel.onUiEvent(AccountScreenUiEvent.Notifications)
        },
        onNavigateToLanguageChange = {
            viewModel.onUiEvent(AccountScreenUiEvent.LanguageChange)
        }
    )

    when (sideEffects) {
        AccountSideEffects.NavigateToLogin -> {
            onNavigateToAuth()
        }

        AccountSideEffects.OpenImagePicker -> {
            LaunchedEffect(sideEffects) {
                resultLauncher.launch("image/*")
            }
        }

        AccountSideEffects.NavigateToNotifications -> {
            onNavigateToNotifications()
        }

        AccountSideEffects.NavigateToLanguageChange -> {
            onNavigateToLanguageChange()
        }

        AccountSideEffects.Nothing -> {}
    }
}

@Composable
fun AccountScreen(
    modifier: Modifier = Modifier,
    context: Context,
    state: AccountState,
    login: () -> Unit,
    logOut: () -> Unit,
    onBack: () -> Unit,
    onChangeUserImage: () -> Unit,
    onNavigateToEditName: () -> Unit,
    onNavigateToEditNumber: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToLanguageChange: () -> Unit,
) {
    when (state.userType) {
        UserType.UNAUTHORIZED ->
            UnauthorizedContent(
                modifier = modifier,
                state = state,
                login = login,
                onNavigateToNotifications = onNavigateToNotifications,
                onNavigateToLanguageChange = onNavigateToLanguageChange,
            )

        else ->
            AuthorizedContent(
                state = state,
                logOut = logOut,
                onBack = onBack,
                onChangeUserImage = onChangeUserImage,
                onNavigateToEditName = onNavigateToEditName,
                onNavigateToEditNumber = onNavigateToEditNumber,
                onNavigateToNotifications = onNavigateToNotifications,
                onNavigateToLanguageChange = onNavigateToLanguageChange,
            )
    }
}

@Composable
fun UnauthorizedContent(
    modifier: Modifier = Modifier,
    state: AccountState,
    login: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToLanguageChange: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        Box(
            modifier = Modifier
                .height(400.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.view_account),
                    style = MaterialTheme.typography.bodyLarge.copy(color = neutral90),
                    textAlign = TextAlign.Center
                )
                AppPrimaryButton(
                    text = stringResource(R.string.enter),
                    onClick = login,
                )
            }
        }

        Setting(
            modifier = modifier,
            state = state,
            onNavigateToNotifications = onNavigateToNotifications,
            onNavigateToLanguageChange = onNavigateToLanguageChange,
        )
    }
}

@Composable
fun AuthorizedContent(
    state: AccountState,
    logOut: () -> Unit,
    onBack: () -> Unit,
    onChangeUserImage: () -> Unit,
    onNavigateToEditName: () -> Unit,
    onNavigateToEditNumber: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToLanguageChange: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.profile_header),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Column {
                Column(
                    modifier = Modifier
                        .height(400.dp)
                        .padding(WindowInsets.statusBars.asPaddingValues())
                        .padding(bottom = 12.dp)
                ) {
                    TopActionBar(
                        logOut = logOut,
                        onBack = onBack
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    UserInfo(
                        state = state,
                        onNavigateToEditName = onNavigateToEditName,
                        onNavigateToEditNumber = onNavigateToEditNumber,
                    )
                }
            }
            UserImage(
                state = state,
                onChangeUserImage = onChangeUserImage
            )
        }
        Setting(
            state = state,
            onNavigateToNotifications = onNavigateToNotifications,
            onNavigateToLanguageChange = onNavigateToLanguageChange,
        )
    }
}

@Composable
fun TopActionBar(
    logOut: () -> Unit,
    onBack: () -> Unit,
){
    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,

        ) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    onBack()
                },
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            tint = neutral10
        )

        Box {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        expanded = true
                    },
                tint = neutral10
            )

            if (expanded) {
                LogOutMenu(
                    expanded = true,
                    onDismiss = { expanded = false },
                    onLogOut = logOut
                )
            }
        }
    }
}

@Composable
fun UserImage(
    state: AccountState,
    onChangeUserImage: () -> Unit,
){
    Box(
        modifier = Modifier
            .size(120.dp)
            .offset(x = 16.dp, y = 140.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .border(width = 2.dp, color = neutral10, shape = CircleShape)
                .clip(CircleShape)
                .background(neutral20),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.account.image == null -> {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.user),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
                state.account.isUrl -> {
                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(data = state.account.image)
                                .apply(block = fun ImageRequest.Builder.() {
                                    placeholder(R.drawable.stadium)
                                    error(R.drawable.error)
                                }).build()
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
                else -> {
                    Image(
                        painter = rememberAsyncImagePainter(state.account.image),
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        AppPrimaryBorderedButton(
            leadingIcon = if (state.account.image != null) R.drawable.pen else R.drawable.add,
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.BottomEnd),
            onClick = onChangeUserImage
        )
    }
}

@Composable
fun UserInfo(
    state: AccountState,
    onNavigateToEditName: () -> Unit,
    onNavigateToEditNumber: () -> Unit,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,

        ) {
        Text(
            text = state.account.name,
            style = MaterialTheme.typography.headlineMedium.copy(color = neutral100)
        )
        AppPrimaryBorderedButton(
            leadingIcon = R.drawable.pen,
            borderColor = neutral10,
            contentPaddingValues = PaddingValues(6.dp),
            modifier = Modifier
                .size(28.dp),
            onClick = onNavigateToEditName
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.phone),
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = state.account.number,
            style = MaterialTheme.typography.bodyLarge.copy(color = neutral90)
        )
        Spacer(modifier = Modifier.weight(1f))

        AppPrimaryBorderedButton(
            leadingIcon = R.drawable.pen,
            borderColor = neutral10,
            contentPaddingValues = PaddingValues(6.dp),
            modifier = Modifier
                .size(28.dp),
            onClick = onNavigateToEditNumber
        )
    }
}

@Composable
fun Setting(
    modifier: Modifier = Modifier,
    state: AccountState,
    onNavigateToNotifications: () -> Unit,
    onNavigateToLanguageChange: () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(neutral20)
        )

        Text(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 8.dp),
            text = stringResource(R.string.settings),
            style = MaterialTheme.typography.titleMedium.copy(color = neutral90)
        )

        SettingRow(
            iconId = R.drawable.notification,
            title = stringResource(R.string.notifications),
            subtitle = stringResource(R.string.notification_settings),
            onClick = onNavigateToNotifications
        )

        SettingRow(
            iconId = R.drawable.language,
            title = stringResource(R.string.language),
            subtitle = stringResource(R.string.uzbek),
            onClick = onNavigateToLanguageChange
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp)
                .height(8.dp)
                .background(neutral20)
        )

        Text(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 8.dp),
            text = stringResource(R.string.about_application),
            style = MaterialTheme.typography.titleMedium.copy(color = neutral90)
        )

        SettingRow(
            iconId = R.drawable.info,
            title = stringResource(R.string.application_version),
            subtitle = state.appVersion,
            onClick = {}
        )
    }
}

@Composable
fun SettingRow(
    iconId: Int,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = modifier
            .height(68.dp)
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = interactionSource
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconId),
            contentDescription = null
        )

        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(color = neutral90)
            )

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(color = neutral90)
            )
        }
    }
}

@Composable
fun LogOutMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onLogOut: () -> Unit
) {
    MaterialTheme(
        shapes = MaterialTheme.shapes.copy(
            extraSmall = RoundedCornerShape(8.dp),
        )
    ) {
        DropdownMenu(
            modifier = Modifier
                .width(182.dp)
                .background(neutral10, shape = RoundedCornerShape(8.dp)),
            expanded = expanded,
            onDismissRequest = onDismiss,
            offset = DpOffset(x = 0.dp, y = 0.dp)
        ) {
            Box(
                modifier = Modifier
                    .clickable(onClick = {
                        onLogOut()
                        onDismiss()
                    })
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(start = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.logout),
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(id = R.string.logout),
                        style = MaterialTheme.typography.bodyLarge.copy(color = neutral90)
                    )
                }
            }
        }
    }
}
