package uz.mobile.footzone.android.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import uz.mobile.footzone.android.R

// Set of Material typography styles to start with

val robotoFontFamily =  FontFamily(
    Font(R.font.roboto_regular),
    Font(R.font.roboto_medium, FontWeight.W500),
    Font(R.font.roboto_bold, FontWeight.W700)
)

val Typography = Typography(
    displayMedium = TextStyle(
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Medium,
        color = neutral100,
        fontSize = 45.sp,
        lineHeight = 52.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Normal,
        color = neutral100,
        fontSize = 32.sp,
        lineHeight = 40.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Normal,
        color = neutral100,
        fontSize = 28.sp,
        lineHeight = 36.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Normal,
        color = neutral100,
        fontSize = 24.sp,
        lineHeight = 32.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Normal,
        color = neutral100,
        fontSize = 22.sp,
        lineHeight = 28.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = (0.1).sp,
        color = neutral90
    ),
    labelLarge = TextStyle(
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = (0.1).sp
    ),
    labelMedium = TextStyle(
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = (0.5).sp,
        color = neutral100
    ),
    labelSmall = TextStyle(

    ),
    bodyLarge = TextStyle(
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = (0.5).sp
    ),
    bodyMedium = TextStyle(
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = (0.25).sp
    ),
    bodySmall = TextStyle(
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = (0.4).sp
    ),
)