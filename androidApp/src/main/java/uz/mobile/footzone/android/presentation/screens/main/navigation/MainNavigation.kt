package uz.mobile.footzone.android.presentation.screens.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import uz.mobile.footzone.android.presentation.screens.main.MainScreenRoute

const val MAIN_ROUTE = "main_route"


fun NavController.navigateToMain(navOptions: NavOptions? = null) = navigate(MAIN_ROUTE, navOptions)

fun NavGraphBuilder.mainScreen(
    onNavigateNotifications: () -> Unit,
    onNavigateOwnerStadiums: () -> Unit
) {
    composable(
        route = MAIN_ROUTE
    ) {
        MainScreenRoute(
            onNavigateNotifications = onNavigateNotifications,
            onNavigateOwnerStadiums = onNavigateOwnerStadiums
        )
    }
}
