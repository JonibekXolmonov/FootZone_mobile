package uz.mobile.footzone.android.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import uz.mobile.footzone.android.R
import uz.mobile.footzone.android.theme.neutral100
import uz.mobile.footzone.android.theme.neutral15
import uz.mobile.footzone.android.theme.neutral90
import uz.mobile.footzone.domain.model.StadiumUiModel

@Composable
fun UpcomingListItem(
    modifier: Modifier = Modifier,
    stadium: StadiumUiModel,
    onViewStadium: (StadiumUiModel) -> Unit,
    onNavigateToStadium: (StadiumUiModel) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(
                color = neutral15,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(all = 16.dp)
    ) {
        Column {
            Text(
                text = stadium.name,
                style = MaterialTheme.typography.titleMedium.copy(color = neutral100)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(top = 12.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.calendar),
                    contentDescription = null
                )

                Text(
                    text = "29-may, chorshanba",
                    style = MaterialTheme.typography.bodyMedium.copy(color = neutral90)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(top = 12.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.date),
                    contentDescription = null
                )

                Text(
                    text = "16:00–18:00, 2 soat",
                    style = MaterialTheme.typography.bodyMedium.copy(color = neutral90)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(top = 12.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.price),
                    contentDescription = null
                )

                Text(
                    text = "100 000 so’m",
                    style = MaterialTheme.typography.bodyMedium.copy(color = neutral90)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            ) {
                AppPrimaryButton(
                    modifier = Modifier.weight(1f),
                    leadingIcon = R.drawable.navigation,
                    text = stringResource(R.string.route),
                    onClick = {
                        onNavigateToStadium(stadium)
                    }
                )

                AppPrimaryBorderedButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.book),
                    onClick = {
                        onViewStadium(stadium)
                    },
                    leadingIcon = R.drawable.stadium_icon
                )
            }
        }
    }
}