package uz.mobile.footzone.android.presentation.screens.stadium_detail.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import uz.mobile.footzone.android.presentation.screens.stadium_detail.StadiumDetailRoute

const val STADIUM_ID = "stadiumId"
const val STADIUM_DETAIL_ROUTE = "stadium_detail_route/{$STADIUM_ID}"

fun NavController.navigateToStadiumDetail(navOptions: NavOptions? = null, stadiumId: Int) {
    navigate("stadium_detail_route/$stadiumId", navOptions)
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.stadiumDetailScreen(
    onBackPressed: () -> Unit,
    onNavigateTimeInterval: () -> Unit,
) {
    composable(
        route = STADIUM_DETAIL_ROUTE,
        arguments = listOf(
            navArgument(STADIUM_ID) { type = NavType.IntType },
        )
    ) { backStackEntry ->
        val stadiumId = backStackEntry.arguments?.getInt(STADIUM_ID)
        StadiumDetailRoute(
            stadiumId = stadiumId ?: 0,
            onBackPressed = onBackPressed,
            onNavigateTimeInterval = onNavigateTimeInterval
        )
    }
}
