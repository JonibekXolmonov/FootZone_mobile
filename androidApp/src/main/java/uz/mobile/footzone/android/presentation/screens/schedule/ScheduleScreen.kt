@file:OptIn(
    ExperimentalMaterial3Api::class
)

package uz.mobile.footzone.android.presentation.screens.schedule

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import uz.mobile.footzone.android.R
import uz.mobile.footzone.android.presentation.components.AppPrimaryButton
import uz.mobile.footzone.android.presentation.components.CompletedListItem
import uz.mobile.footzone.android.presentation.components.UpcomingListItem
import uz.mobile.footzone.android.presentation.utils.shareStadiumLocationToNavigators
import uz.mobile.footzone.android.theme.blue600
import uz.mobile.footzone.android.theme.neutral10
import uz.mobile.footzone.android.theme.neutral100
import uz.mobile.footzone.android.theme.neutral40
import uz.mobile.footzone.android.theme.neutral80
import uz.mobile.footzone.android.theme.neutral90
import uz.mobile.footzone.domain.model.StadiumUiModel
import uz.mobile.footzone.domain.model.UserType
import uz.mobile.footzone.presentation.schedule.ScheduleScreenUiEvent
import uz.mobile.footzone.presentation.schedule.ScheduleSideEffects
import uz.mobile.footzone.presentation.schedule.ScheduleState

@Composable
fun ScheduleRoute(
    modifier: Modifier = Modifier,
    viewModel: ScheduleViewModel = koinViewModel(),
    context: Context = LocalContext.current,
    onNavigateToAuth: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val sideEffects by viewModel.sideEffect.collectAsStateWithLifecycle(ScheduleSideEffects.Nothing)

    ScheduleScreen(
        modifier = modifier.fillMaxSize(),
        state = state,
        login = {
            viewModel.onUiEvent(ScheduleScreenUiEvent.Login)
        },
        onViewStadium = {
            viewModel.onUiEvent(ScheduleScreenUiEvent.ViewStadium(it))
        },
        onNavigateToStadium = {
            viewModel.onUiEvent(ScheduleScreenUiEvent.NavigateToStadium(it))
        },

        )

    when (sideEffects) {

        ScheduleSideEffects.NavigateToLogin -> {
            onNavigateToAuth()
        }

        is ScheduleSideEffects.OpenNavigatorChoose -> {
            LaunchedEffect(sideEffects) {
                val location =
                    (sideEffects as ScheduleSideEffects.OpenNavigatorChoose).stadiumLocation
                context.shareStadiumLocationToNavigators(location)
            }
        }

        ScheduleSideEffects.Nothing -> {}
    }
}

@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    state: ScheduleState,
    login: () -> Unit,
    onViewStadium: (StadiumUiModel) -> Unit,
    onNavigateToStadium: (StadiumUiModel) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                title = {
                    Text(
                        stringResource(R.string.stadiums),
                        style = MaterialTheme.typography.titleLarge.copy(color = neutral100)
                    )
                },
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (state.userType) {
                    UserType.UNAUTHORIZED -> UnauthorizedContent(login)

                    else ->
                        AuthorizedContent(
                            modifier = modifier,
                            state = state,
                            onViewStadium = onViewStadium,
                            onNavigateToStadium = onNavigateToStadium
                        )
                }
            }
        }
    )
}


@Composable
fun UnauthorizedContent(login: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.view_stadiums_table),
            style = MaterialTheme.typography.bodyLarge.copy(color = neutral90),
            textAlign = TextAlign.Center
        )
        AppPrimaryButton(
            text = stringResource(R.string.enter),
            onClick = login,
        )
    }
}

@Composable
fun AuthorizedContent(
    modifier: Modifier,
    state: ScheduleState,
    onViewStadium: (StadiumUiModel) -> Unit,
    onNavigateToStadium: (StadiumUiModel) -> Unit,
) {
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        stringResource(R.string.played),
        stringResource(R.string.to_play)
    )
    Column(modifier = modifier.fillMaxWidth()) {
        TabRow(
            selectedTabIndex = tabIndex,
            modifier = Modifier
                .height(56.dp)
                .background(neutral10),
            containerColor = Color.Transparent,
            contentColor = neutral10,
            indicator = { tabPositions ->
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[tabIndex])
                        .height(2.dp)
                        .padding(horizontal = 16.dp)
                        .background(
                            color = blue600,
                            shape = RoundedCornerShape(
                                topStart = 2.dp,
                                topEnd = 2.dp
                            )
                        )
                )
            },
            divider = {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = neutral40
                )
            },
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = if (tabIndex == index) neutral100 else neutral80
                            )
                        )
                    },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },

                    )
            }
        }
        when (tabIndex) {
            0 -> UpcomingTab(
                stadiums = state.stadiums,
                onViewStadium = onViewStadium,
                onNavigateToStadium = onNavigateToStadium
            )

            1 -> CompletedTab(
                stadiums = state.stadiums
            )
        }
    }
}


@Composable
fun UpcomingTab(
    stadiums: List<StadiumUiModel>,
    onViewStadium: (StadiumUiModel) -> Unit,
    onNavigateToStadium: (StadiumUiModel) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(stadiums) {
            UpcomingListItem(
                stadium = it,
                onViewStadium = onViewStadium,
                onNavigateToStadium = onNavigateToStadium
            )
        }
    }
}

@Composable
fun CompletedTab(
    stadiums: List<StadiumUiModel>,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(stadiums) {
            CompletedListItem(
                stadium = it
            )
        }
    }
}




















