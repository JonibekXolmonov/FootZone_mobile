package uz.mobile.footzone.android.presentation.screens.auth.reset_password.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import uz.mobile.footzone.android.presentation.screens.auth.register.RegistrationRoute
import uz.mobile.footzone.android.presentation.screens.auth.reset_password.ResetPasswordRoute

const val PASSWORD_RESET_ROUTE = "password_reset_route"

fun NavController.navigateToPasswordReset(navOptions: NavOptions? = null) =
    navigate(PASSWORD_RESET_ROUTE, navOptions)

fun NavGraphBuilder.passwordResetScreen(
    onBackPressed: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    composable(
        route = PASSWORD_RESET_ROUTE
    ) {
        ResetPasswordRoute(
            onBackPressed = onBackPressed,
            onNavigateToLogin = onNavigateToLogin
        )
    }
}