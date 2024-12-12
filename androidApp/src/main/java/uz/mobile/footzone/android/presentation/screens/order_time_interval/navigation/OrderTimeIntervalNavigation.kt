package uz.mobile.footzone.android.presentation.screens.order_time_interval.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import uz.mobile.footzone.android.presentation.screens.order_time_interval.OrderTimeIntervalRoute

const val ORDER_TIME_INTERVAL_ROUTE = "order_time_interval"

fun NavController.navigateToOrderTimeInterval(navOptions: NavOptions? = null) {
    navigate(ORDER_TIME_INTERVAL_ROUTE, navOptions)
}

fun NavGraphBuilder.orderTimeIntervalScreen(
    onBackPressed: () -> Unit
) {
    composable(
        route = ORDER_TIME_INTERVAL_ROUTE,

        ) {
        OrderTimeIntervalRoute(
            onBackPressed = onBackPressed
        )
    }
}
