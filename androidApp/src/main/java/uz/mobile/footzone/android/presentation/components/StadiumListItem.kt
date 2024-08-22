package uz.mobile.footzone.android.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.mobile.footzone.android.R
import uz.mobile.footzone.android.common.blurredShadow
import uz.mobile.footzone.android.theme.MyApplicationTheme
import uz.mobile.footzone.android.theme.green600
import uz.mobile.footzone.android.theme.neutral10
import uz.mobile.footzone.android.theme.neutral100
import uz.mobile.footzone.android.theme.neutral60
import uz.mobile.footzone.android.theme.neutral80
import uz.mobile.footzone.android.theme.shadow10
import uz.mobile.footzone.domain.model.StadiumUiModel

@Composable
fun StadiumListItem(
    modifier: Modifier = Modifier,
    stadium: StadiumUiModel,
    onAddStadiumBookmark: (StadiumUiModel) -> Unit,
    onBookStadium: (StadiumUiModel) -> Unit,
    onNavigateToStadium: (StadiumUiModel) -> Unit,
) {
    var rating by remember {
        mutableFloatStateOf(1f)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(neutral10)
            .padding(vertical = 16.dp)
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(4) {
                when (it) {
                    0 -> StadiumImage(
                        modifier = Modifier.padding(start = 16.dp)
                    )

                    3 -> StadiumImage(
                        modifier = Modifier.padding(end = 16.dp)
                    )

                    else -> StadiumImage()
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 16.dp, end = 16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stadium.name,
                    style = MaterialTheme.typography.titleMedium.copy(color = neutral100)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 2.dp)
                ) {
                    StarRatingBar(rating = rating) {
                        rating = it
                    }
                    Text(
                        text = "(81)",
                        style = MaterialTheme.typography.bodySmall.copy(color = neutral80)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(
                        text = "Ochiq",
                        style = MaterialTheme.typography.bodySmall.copy(color = green600)
                    )
                    Text(
                        text = "·",
                        style = MaterialTheme.typography.bodySmall.copy(color = neutral60)
                    )
                    Text(
                        text = "00:00 da yopiladi",
                        style = MaterialTheme.typography.bodySmall.copy(color = neutral80)
                    )
                }

                Text(
                    text = "50 000 so’m/soat",
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                    modifier = Modifier.padding(top = 4.dp)
                )

            }

            AppPrimaryBorderedButton(
                leadingIcon = R.drawable.bookmark_add,
                modifier = Modifier.size(40.dp),
                onClick = {
                    onAddStadiumBookmark(stadium)
                }
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
        ) {
            AppPrimaryButton(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.book),
                onClick = {
                    onBookStadium(stadium)
                },
                leadingIcon = R.drawable.book
            )

            AppPrimaryBorderedButton(
                modifier = Modifier.weight(1f),
                leadingIcon = R.drawable.navigation,
                text = stringResource(R.string.route),
                onClick = {
                    onNavigateToStadium(stadium)
                }
            )
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
            .size(160.dp, 120.dp)
            .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop
    )
}

@Preview
@Composable
fun StadiumListItemPr(modifier: Modifier = Modifier) {
    MyApplicationTheme {
        StadiumListItem(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .blurredShadow(
                    color = shadow10,
                    shadowBlurRadius = 8.dp,
                    offsetY = 4.dp
                ),
            stadium = StadiumUiModel(id = 1, name = "uxlaa"),
            {},
            {},
            {}
        )
    }
}