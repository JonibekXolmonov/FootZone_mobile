package uz.mobile.footzone.android.presentation.screens.app_sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import uz.mobile.footzone.android.R
import uz.mobile.footzone.android.common.blurredShadow
import uz.mobile.footzone.android.presentation.screens.main.ActionContainer
import uz.mobile.footzone.presentation.main.BottomSheetAction
import uz.mobile.footzone.android.presentation.screens.main.SearchInputField
import uz.mobile.footzone.android.theme.neutral10
import uz.mobile.footzone.android.theme.shadowBottomSheet

@Composable
fun HomeScreenActionBottomSheet(
    modifier: Modifier = Modifier,
    isUserStadiumOwner: Boolean,
    query: String,
    onSearchValueChanged: (String) -> Unit,
    onActionPerformed: (BottomSheetAction) -> Unit,
    performSearch: () -> Unit
) {

    Column(
        modifier = modifier
            .blurredShadow(
                color = shadowBottomSheet,
                shadowBlurRadius = 4.dp,
                offsetY = (-2).dp
            )
            .clip(RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp))
            .wrapContentHeight()
            .background(neutral10)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        DragHandler()

        SearchInputField(
            value = query,
            label = stringResource(R.string.search_field_placeholder),
            onValueChange = onSearchValueChanged,
            performSearch = performSearch
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ActionContainer(
                    modifier = Modifier.weight(1f),
                    icon = R.drawable.near_me,
                    label = R.string.near_stadiums,
                    onClick = {
                        onActionPerformed(BottomSheetAction.NearStadiums)
                    }
                )

                ActionContainer(
                    modifier = Modifier.weight(1f),
                    icon = R.drawable.bookmark,
                    label = R.string.marked_stadiums,
                    onClick = {
                        onActionPerformed(BottomSheetAction.SavedStadiums)
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ActionContainer(
                    modifier = Modifier.weight(1f),
                    icon = R.drawable.history,
                    label = R.string.previously_booked_stadiums,
                    onClick = {
                        onActionPerformed(BottomSheetAction.PreviouslyBookedStadiums)
                    }
                )

                if (isUserStadiumOwner) {
                    ActionContainer(
                        modifier = Modifier.weight(1f),
                        icon = R.drawable.history,
                        label = R.string.my_stadiums,
                        onClick = {
                            onActionPerformed(BottomSheetAction.MyStadiums)
                        }
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}