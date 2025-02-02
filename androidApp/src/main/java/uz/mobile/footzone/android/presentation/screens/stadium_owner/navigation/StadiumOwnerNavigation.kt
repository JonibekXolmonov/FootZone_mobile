package uz.mobile.footzone.android.presentation.screens.stadium_owner.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import uz.mobile.footzone.android.presentation.screens.stadium_detail.StadiumDetailRoute
import uz.mobile.footzone.android.presentation.screens.stadium_owner.StadiumOwnerRoute

const val STADIUM_DETAIL_ROUTE = "stadium_owner"

fun NavController.navigateToStadiumOwner(navOptions: NavOptions? = null) {
    navigate(STADIUM_DETAIL_ROUTE, navOptions)
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.stadiumOwnerScreen(
    onBackPressed: () -> Unit,
) {
    composable(
        route = STADIUM_DETAIL_ROUTE,

    ) {
        StadiumOwnerRoute(
            onBackPressed = onBackPressed,
        )
    }
}
