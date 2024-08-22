package uz.mobile.footzone.android.presentation.screens.auth.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import uz.mobile.footzone.android.presentation.screens.auth.login.LoginRoute

const val LOGIN_ROUTE = "login_route"


fun NavController.navigateToLogin(navOptions: NavOptions? = null) =
    navigate(LOGIN_ROUTE, navOptions)

fun NavGraphBuilder.loginScreen(
    onBackPressed: () -> Unit,
    onForgetPassword: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToResetPassword: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    composable(
        route = LOGIN_ROUTE
    ) {
        LoginRoute(
            onBackPressed = onBackPressed,
            onForgetPassword = onForgetPassword,
            onNavigateToRegister = onNavigateToRegister,
            onNavigateToResetPassword = onNavigateToResetPassword,
            onLoginSuccess = onLoginSuccess
        )
    }
}