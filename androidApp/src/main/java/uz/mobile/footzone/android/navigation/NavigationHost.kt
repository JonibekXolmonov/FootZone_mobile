package uz.mobile.footzone.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import uz.mobile.footzone.android.presentation.screens.account.AccountScreen
import uz.mobile.footzone.android.presentation.screens.main.MainScreen
import uz.mobile.footzone.android.presentation.screens.schedule.ScheduleScreen

@Composable
fun NavigationHost(modifier: Modifier = Modifier, navController: NavHostController) {

    NavHost(navController, startDestination = BottomNavItem.Main.route, modifier = modifier) {
        composable(BottomNavItem.Main.route) { MainScreen() }
        composable(BottomNavItem.Schedule.route) { ScheduleScreen() }
        composable(BottomNavItem.Profile.route) { AccountScreen() }
    }
}