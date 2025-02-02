package uz.mobile.footzone.android.presentation.screens.stadium_owner

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import uz.mobile.footzone.android.R
import uz.mobile.footzone.android.presentation.components.AppTopBar
import uz.mobile.footzone.android.presentation.screens.schedule.AuthorizedContent
import uz.mobile.footzone.android.presentation.screens.schedule.UnauthorizedContent
import uz.mobile.footzone.android.theme.neutral90
import uz.mobile.footzone.domain.model.UserType
import uz.mobile.footzone.presentation.stadium_owner.StadiumOwnerState

@Composable
fun StadiumOwnerRoute(
    modifier: Modifier = Modifier,
    viewModel: StadiumOwnerViewModel = koinViewModel(),
    context: Context = LocalContext.current,
    onBackPressed: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    StadiumOwnerScreen(
        modifier = modifier.fillMaxSize(),
        state = state,
        onBackPressed = onBackPressed
    )

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.sideEffect.collectLatest {
                when (it) {

                    else -> {}
                }
            }
        }
    }
}


@Composable
fun StadiumOwnerScreen(
    modifier: Modifier = Modifier,
    state: StadiumOwnerState,
    onBackPressed: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.my_stadiums),
                onBack = onBackPressed,
                actionIcon = R.drawable.add_icon,
                onActionPressed = {

                }
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
//                when (state.userType) {
//                    UserType.UNAUTHORIZED -> EmptyStadiumsContent()
//
//                    else ->
//                        StadiumsContent(
//
//                        )
//                }

                EmptyStadiumsContent()
            }
        }
    )
}

@Composable
fun EmptyStadiumsContent() {
    Text(
        text = stringResource(R.string.stadiums_not_available),
        style = MaterialTheme.typography.bodyLarge.copy(color = neutral90),
        textAlign = TextAlign.Center
    )
}

@Composable
fun StadiumsContent() {

}