@file:OptIn(ExperimentalMaterial3Api::class)

package uz.mobile.footzone.android.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import uz.mobile.footzone.android.R
import uz.mobile.footzone.android.common.blurredShadow
import uz.mobile.footzone.android.theme.blue600
import uz.mobile.footzone.android.theme.neutral10
import uz.mobile.footzone.android.theme.neutral100
import uz.mobile.footzone.android.theme.neutral40
import uz.mobile.footzone.android.theme.neutral80
import uz.mobile.footzone.android.theme.shadow
import uz.mobile.footzone.android.theme.yellow500

@Composable
fun AppPrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    leadingIcon: Int? = null,
    onClick: () -> Unit,
    shape: Shape = MaterialTheme.shapes.extraLarge,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
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
            Text(text = text, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
fun AppPrimaryBorderedButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    leadingIcon: Int? = null,
    shape: Shape = CircleShape,
    contentColor: Color = blue600,
    contentPaddingValues: PaddingValues = PaddingValues(10.dp),
    containerColor: Color = neutral10,
    borderColor: Color = neutral80,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        contentPadding = contentPaddingValues,
        shape = shape,
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        border = BorderStroke(1.dp, borderColor),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (leadingIcon != null)
                Icon(
                    imageVector = ImageVector.vectorResource(id = leadingIcon),
                    contentDescription = null
                )

            if (text != null)
                Text(text = text, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onBack: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        title = {
            Text(title, style = MaterialTheme.typography.titleLarge.copy(color = neutral100))
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
}

@Composable
fun AppCircleButton(modifier: Modifier = Modifier, @DrawableRes icon: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .size(48.dp)
            .blurredShadow(
                color = shadow,
                shadowBlurRadius = 4.dp,
                offsetY = 2.dp,
                cornersRadius = 24.dp
            ),
        contentPadding = PaddingValues(12.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = neutral10,
            contentColor = neutral100
        )
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = icon),
            contentDescription = null
        )
    }
}

@Composable
fun StarRatingBar(
    maxStars: Int = 5,
    rating: Float,
    onRatingChanged: (Float) -> Unit
) {
    Row(
        modifier = Modifier.selectableGroup(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating
            val icon: ImageVector =
                ImageVector.vectorResource(id = if (isSelected) R.drawable.star_filled else R.drawable.star_border)
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = yellow500,
                modifier = Modifier
                    .selectable(
                        selected = isSelected,
                        onClick = {
                            onRatingChanged(i.toFloat())
                        }
                    )
                    .size(16.dp)
            )
        }
    }
}