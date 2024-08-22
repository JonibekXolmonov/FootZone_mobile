package uz.mobile.footzone.android.presentation.screens.auth.otp.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import uz.mobile.footzone.android.presentation.screens.auth.otp.OTPValidationRoute

const val PHONE_NUMBER = "phoneNumber"
const val OTP_ROUTE = "otp_route/{$PHONE_NUMBER}"


fun NavController.navigateToOtp(navOptions: NavOptions? = null) = navigate(OTP_ROUTE, navOptions)

fun NavGraphBuilder.otpScreen(
    onBackPressed: () -> Unit,
    onNavigateToPasswordReset: () -> Unit,
    onOtpVerified: () -> Unit
) {
    composable(
        route = OTP_ROUTE,
        arguments = listOf(
            navArgument(PHONE_NUMBER) { type = NavType.StringType },
        )
    ) {
        OTPValidationRoute(
            onBackPressed = onBackPressed,
            onNavigateToPasswordReset = onNavigateToPasswordReset,
            onOtpVerified = onOtpVerified
        )
    }
}