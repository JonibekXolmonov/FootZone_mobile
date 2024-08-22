package uz.mobile.footzone.android.presentation.screens.app_sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.mobile.footzone.android.R
import uz.mobile.footzone.android.common.blurredShadow
import uz.mobile.footzone.android.presentation.components.AppPrimaryBorderedButton
import uz.mobile.footzone.android.theme.MyApplicationTheme
import uz.mobile.footzone.android.theme.blue100
import uz.mobile.footzone.android.theme.neutral10
import uz.mobile.footzone.android.theme.neutral100
import uz.mobile.footzone.android.theme.neutral80
import uz.mobile.footzone.android.theme.neutral90
import uz.mobile.footzone.android.theme.shadow6
import uz.mobile.footzone.android.theme.shadow10

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenTopSheet(
    modifier: Modifier = Modifier,
    shadowColor: Color = shadow10,
    currentlyOpenFilterActive: Boolean,
    wellRatedFilterActive: Boolean,
    onBack: () -> Unit,
    onCurrentlyOpenSelected: () -> Unit,
    onWellRatedSelected: () -> Unit,
) {
    Column(
        modifier = modifier
            .blurredShadow(
                color = shadowColor,
                shadowBlurRadius = 8.dp,
                offsetY = 4.dp
            )
            .background(neutral10)
            .padding(bottom = 16.dp)
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
            title = {
                Text(
                    stringResource(id = R.string.near_stadiums),
                    style = MaterialTheme.typography.titleLarge.copy(color = neutral100)
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )

        Row(
            modifier = Modifier.padding(start = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            AppPrimaryBorderedButton(
                text = stringResource(R.string.now_open),
                shape = MaterialTheme.shapes.medium,
                contentPaddingValues = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                contentColor = if (currentlyOpenFilterActive) neutral100 else neutral90,
                containerColor = if (currentlyOpenFilterActive) blue100 else neutral10,
                borderColor = if (currentlyOpenFilterActive) blue100 else neutral80,
                onClick = onCurrentlyOpenSelected,
                leadingIcon = if (currentlyOpenFilterActive) R.drawable.tick else null
            )

            AppPrimaryBorderedButton(
                text = stringResource(R.string.well_rated),
                shape = MaterialTheme.shapes.medium,
                contentPaddingValues = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                contentColor = if (wellRatedFilterActive) neutral100 else neutral90,
                containerColor = if (wellRatedFilterActive) blue100 else neutral10,
                borderColor = if (wellRatedFilterActive) blue100 else neutral80,
                onClick = onWellRatedSelected,
                leadingIcon = if (wellRatedFilterActive) R.drawable.tick else null
            )
        }
    }
}

@Preview
@Composable
fun MainScreenTopSheetPr(modifier: Modifier = Modifier) {
    MyApplicationTheme {
        MainScreenTopSheet(
            modifier = Modifier.fillMaxWidth(),
            onBack = {},
            shadowColor = shadow10,
            onWellRatedSelected = {},
            onCurrentlyOpenSelected = {},
            wellRatedFilterActive = false,
            currentlyOpenFilterActive = false
        )
    }
}