package uz.mobile.footzone.android.common

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


fun Modifier.blurredShadow(
    color: Color,
    alpha: Float = .08f,
    cornersRadius: Dp = 0.dp,
    shadowBlurRadius: Dp = (11.6).dp,
    offsetY: Dp = 8.dp,
    offsetX: Dp = 0.dp
) = drawBehind {

    val shadowColor = color.copy(alpha = alpha).toArgb()
    val transparentColor = color.copy(alpha = 0f).toArgb()

    drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowBlurRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.drawRoundRect(
            0f,
            0f,
            this.size.width,
            this.size.height,
            cornersRadius.toPx(),
            cornersRadius.toPx(),
            paint
        )
    }
}

fun Modifier.swipeUpDown(
    onScrollDown: () -> Unit,
    onScrollUp: () -> Unit,
) =
    pointerInput(Unit) {
        detectVerticalDragGestures(
            onVerticalDrag = { _, dragAmount ->
                if (dragAmount > 0) onScrollDown() else onScrollUp()
            }
        )
    }
