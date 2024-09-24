package uz.mobile.footzone.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import uz.mobile.footzone.android.presentation.screens.account.navigation.accountScreen
import uz.mobile.footzone.android.presentation.screens.auth.login.navigation.loginScreen
import uz.mobile.footzone.android.presentation.screens.auth.login.navigation.navigateToLogin
import uz.mobile.footzone.android.presentation.screens.auth.otp.navigation.navigateToOtp
import uz.mobile.footzone.android.presentation.screens.auth.otp.navigation.otpScreen
import uz.mobile.footzone.android.presentation.screens.auth.password_recover.navigation.navigateToPasswordRecover
import uz.mobile.footzone.android.presentation.screens.auth.password_recover.navigation.passwordRecoverScreen
import uz.mobile.footzone.android.presentation.screens.auth.register.navigation.navigateToRegister
import uz.mobile.footzone.android.presentation.screens.auth.register.navigation.registerScreen
import uz.mobile.footzone.android.presentation.screens.auth.reset_password.navigation.navigateToPasswordReset
import uz.mobile.footzone.android.presentation.screens.auth.reset_password.navigation.passwordResetScreen
import uz.mobile.footzone.android.presentation.screens.main.navigation.mainScreen
import uz.mobile.footzone.android.presentation.screens.main.navigation.navigateToMain
import uz.mobile.footzone.android.presentation.screens.schedule.navigation.scheduleScreen


@Composable
fun MainNavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {

    NavHost(navController, startDestination = BottomNavItem.Main.route, modifier = modifier) {

        mainScreen(
            onNavigateNotifications = {},
            onNavigateOwnerStadiums = {},
            onNavigateToAuth = navController::navigateToLogin
        )

        scheduleScreen(
            onNavigateToAuth = navController::navigateToLogin
        )

        accountScreen()

        registerScreen(
            onBackPressed = navController::popBackStack,
            onNavigateToLogin = navController::navigateToLogin,
            onRegisteredSuccessfully = navController::navigateToMain
        )

        loginScreen(
            onBackPressed = navController::popBackStack,
            onLoginSuccess = navController::navigateToMain,
            onNavigateToRegister = navController::navigateToRegister,
            onNavigateToResetPassword = navController::navigateToPasswordReset,
            onForgetPassword = navController::navigateToPasswordRecover
        )

        otpScreen(
            onBackPressed = navController::popBackStack,
            onNavigateToPasswordReset = navController::navigateToPasswordReset,
            onOtpVerified = navController::navigateToMain
        )

        passwordRecoverScreen(
            onBackPressed = navController::popBackStack,
            onNavigateToOTPValidation = navController::navigateToOtp
        )

        passwordResetScreen(
            onBackPressed = navController::popBackStack,
            onNavigateToLogin = navController::navigateToLogin
        )
    }
}