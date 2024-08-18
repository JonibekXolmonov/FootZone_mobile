package uz.mobile.footzone.android.presentation.screens.app_sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import uz.mobile.footzone.android.theme.neutral30

@Composable
fun DragHandler(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(32.dp, 4.dp)
            .clip(MaterialTheme.shapes.extraSmall)
            .background(neutral30)
    )
}