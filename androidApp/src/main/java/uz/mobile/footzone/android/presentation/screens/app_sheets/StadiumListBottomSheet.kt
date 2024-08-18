package uz.mobile.footzone.android.presentation.screens.app_sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import uz.mobile.footzone.android.common.blurredShadow
import uz.mobile.footzone.android.presentation.components.StadiumListItem
import uz.mobile.footzone.android.theme.MyApplicationTheme
import uz.mobile.footzone.android.theme.neutral10
import uz.mobile.footzone.android.theme.neutral20
import uz.mobile.footzone.android.theme.shadowBottomSheet
import uz.mobile.footzone.domain.model.StadiumUiModel

@Composable
fun StadiumListBottomSheet(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp,
    shadowColor: Color = shadowBottomSheet,
    stadiums: List<StadiumUiModel>,
    onAddStadiumBookmark: (StadiumUiModel) -> Unit,
    onBookStadium: (StadiumUiModel) -> Unit,
    onNavigateToStadium: (StadiumUiModel) -> Unit,
) {
    Column(
        modifier = modifier
            .blurredShadow(
                color = shadowColor,
                shadowBlurRadius = 4.dp,
                offsetY = (-2).dp
            )
            .clip(RoundedCornerShape(topEnd = cornerRadius, topStart = cornerRadius))
            .background(neutral10)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

            DragHandler()

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.background(neutral20)
        ) {
            items(stadiums) {
                StadiumListItem(
                    stadium = it,
                    onBookStadium = onBookStadium,
                    onAddStadiumBookmark = onAddStadiumBookmark,
                    onNavigateToStadium = onNavigateToStadium
                )
            }
        }
    }
}

@Preview
@Composable
fun StadiumListBottomSheetPr(modifier: Modifier = Modifier) {
    MyApplicationTheme {
        StadiumListBottomSheet(
            stadiums = listOf(
                StadiumUiModel(id = 1, name = "demo 1", 69.2401, 41.2995),
                StadiumUiModel(id = 2, name = "demo 2", 69.2451, 41.2895),
                StadiumUiModel(id = 3, name = "demo 3", 69.2421, 41.2965),
                StadiumUiModel(id = 4, name = "demo 4", 69.2441, 41.2935),
                StadiumUiModel(id = 5, name = "demo 5", 69.2471, 41.2905),
            ),
            onNavigateToStadium = {},
            onBookStadium = {},
            onAddStadiumBookmark = {},
        )
    }
}