package uz.mobile.footzone.android.presentation.screens.order_time_interval

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import uz.mobile.footzone.android.R
import uz.mobile.footzone.android.presentation.components.AppTopBar
import uz.mobile.footzone.android.presentation.components.TimePickerField
import uz.mobile.footzone.android.theme.MyApplicationTheme
import uz.mobile.footzone.android.theme.blue50
import uz.mobile.footzone.android.theme.blue600
import uz.mobile.footzone.android.theme.neutral10
import uz.mobile.footzone.android.theme.neutral100
import uz.mobile.footzone.android.theme.neutral20
import uz.mobile.footzone.android.theme.neutral30
import uz.mobile.footzone.android.theme.neutral60
import uz.mobile.footzone.presentation.order_time_interval.OrderTimeIntervalState

@Composable
fun OrderTimeIntervalRoute(
    modifier: Modifier = Modifier,
    viewModel: OrderTimeIntervalViewModel = koinViewModel(),
    context: Context = LocalContext.current,
    onBackPressed: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    OrderTimeIntervalScreen(
        modifier = modifier.fillMaxSize(),
        state = state,
        onBackPressed = onBackPressed
    )

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.sideEffect.collectLatest {

            }
        }
    }
}

@Composable
fun OrderTimeIntervalScreen(
    modifier: Modifier = Modifier,
    state: OrderTimeIntervalState,
    onBackPressed: () -> Unit
) {

    val timesWithStatus = listOf(
        "14:00" to ItemStatus.Disabled,
        "14:30" to ItemStatus.Disabled,
        "15:00" to ItemStatus.Default,
        "15:30" to ItemStatus.Default,
        "16:00" to ItemStatus.Default,
        "16:30" to ItemStatus.Default,
        "17:00" to ItemStatus.Default,
        "17:30" to ItemStatus.Disabled,
        "18:00" to ItemStatus.Disabled,
        "18:30" to ItemStatus.Disabled,
        "19:00" to ItemStatus.Default,
        "19:30" to ItemStatus.Default,
        "20:00" to ItemStatus.Default,
        "20:30" to ItemStatus.Disabled,
        "21:00" to ItemStatus.Selected,
        "21:30" to ItemStatus.Alternative,
        "22:00" to ItemStatus.Alternative,
        "22:30" to ItemStatus.Alternative,
        "23:00" to ItemStatus.Selected,
        "23:30" to ItemStatus.Disabled
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.time_interval),
                onBack = onBackPressed,
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    TimePickerField(
                        modifier = Modifier.weight(1f),
                        hint = stringResource(R.string.from),
                        onClick = {

                        },
                        value = "21:00",
                        clearIcon = R.drawable.text_filed_clear,
                        onClearTextField = {

                        }
                    )

                    TimePickerField(
                        modifier = Modifier.weight(1f),
                        hint = stringResource(R.string.until),
                        value = "23:00",
                        onClick = {

                        },
                        clearIcon = R.drawable.text_filed_clear,
                        onClearTextField = {

                        }
                    )
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(timesWithStatus) { (time, status)  ->
                        TimeIntervalItem(
                            time = time,
                            status = status,
                            onItemPressed = {

                            }
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun TimeIntervalItem(
    time: String,
    status: ItemStatus,
    onItemPressed: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .border(width = 1.dp, color = status.borderColor, shape = MaterialTheme.shapes.small)
            .background(color = status.backgroundColor, shape = MaterialTheme.shapes.small)
            .clickable {
                onItemPressed()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = time,
            style = MaterialTheme.typography.titleLarge.copy(color = status.textColor)
        )
    }
}

enum class ItemStatus(val backgroundColor: Color, val textColor: Color, val borderColor: Color) {
    Selected(
        backgroundColor = blue600,
        textColor = neutral10,
        borderColor = blue600
    ),
    Disabled(
        backgroundColor = neutral20,
        textColor = neutral60,
        borderColor = neutral30
    ),
    Default(
        backgroundColor = neutral10,
        textColor = neutral100,
        borderColor = neutral30
    ),
    Alternative(
        backgroundColor = blue50,
        textColor = blue600,
        borderColor = blue600
    );

}
