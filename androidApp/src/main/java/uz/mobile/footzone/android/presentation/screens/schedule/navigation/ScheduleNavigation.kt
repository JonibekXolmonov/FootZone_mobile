package uz.mobile.footzone.android.presentation.screens.schedule.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import uz.mobile.footzone.android.presentation.screens.main.MainScreenRoute
import uz.mobile.footzone.android.presentation.screens.schedule.ScheduleRoute

const val SCHEDULE_ROUTE = "schedule_route"


fun NavController.navigateToSchedule(navOptions: NavOptions) = navigate(SCHEDULE_ROUTE, navOptions)

fun NavGraphBuilder.scheduleScreen(

) {
    composable(
        route = SCHEDULE_ROUTE
    ) {
        ScheduleRoute()
    }
}
