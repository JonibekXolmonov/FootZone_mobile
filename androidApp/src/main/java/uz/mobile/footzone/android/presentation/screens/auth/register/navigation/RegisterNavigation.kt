package uz.mobile.footzone.android.presentation.screens.auth.register.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import uz.mobile.footzone.android.presentation.screens.auth.register.RegistrationRoute

const val REGISTER_ROUTE = "register_route"

fun NavController.navigateToRegister(navOptions: NavOptions? = null) =
    navigate(REGISTER_ROUTE, navOptions)

fun NavGraphBuilder.registerScreen(
    onBackPressed: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onRegisteredSuccessfully: () -> Unit
) {
    composable(
        route = REGISTER_ROUTE
    ) {
        RegistrationRoute(
            onBackPressed = onBackPressed,
            onNavigateToLogin = onNavigateToLogin,
            onRegisteredSuccessfully = onRegisteredSuccessfully
        )
    }
}