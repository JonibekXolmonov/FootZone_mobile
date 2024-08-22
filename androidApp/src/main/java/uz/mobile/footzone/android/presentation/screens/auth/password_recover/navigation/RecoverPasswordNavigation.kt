package uz.mobile.footzone.android.presentation.screens.auth.password_recover.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import uz.mobile.footzone.android.presentation.screens.auth.password_recover.PasswordRecoverRoute

const val PASSWORD_RECOVER_ROUTE = "password_recover_route"


fun NavController.navigateToPasswordRecover(navOptions: NavOptions? = null) =
    navigate(PASSWORD_RECOVER_ROUTE, navOptions)

fun NavGraphBuilder.passwordRecoverScreen(
    onBackPressed: () -> Unit,
    onNavigateToOTPValidation: () -> Unit
) {
    composable(
        route = PASSWORD_RECOVER_ROUTE
    ) {
        PasswordRecoverRoute(
            onBackPressed = onBackPressed,
            onNavigateToOTPValidation = onNavigateToOTPValidation
        )
    }
}