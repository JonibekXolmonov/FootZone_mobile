package uz.mobile.footzone.android.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import uz.mobile.footzone.android.theme.blue600
import uz.mobile.footzone.android.theme.neutral10
import uz.mobile.footzone.android.theme.neutral40
import uz.mobile.footzone.android.theme.neutral80

@Composable
fun AppPrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    leadingIcon: Int? = null,
    onClick: () -> Unit,
    enabled: Boolean
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        contentPadding = PaddingValues(
            vertical = 10.dp, horizontal = 24.dp
        ),
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = blue600,
            disabledContainerColor = neutral40,
            contentColor = neutral10,
            disabledContentColor = neutral80
        ),
        enabled = enabled
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (leadingIcon != null) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = leadingIcon),
                    contentDescription = null
                )
            }
            Text(text = text)
        }
    }
}

@Composable
fun VerticalSpacer(modifier: Modifier = Modifier) {
    HorizontalDivider(modifier, color = Transparent)
}