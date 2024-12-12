package uz.mobile.footzone.android.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import uz.mobile.footzone.android.navigation.BottomBar
import uz.mobile.footzone.android.navigation.BottomNavItem
import uz.mobile.footzone.android.navigation.MainNavigationHost

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FootZoneApp(
    navController: NavHostController = rememberNavController(),
) {
    val currentDestination by navController.currentBackStackEntryAsState()

    val screensWithBottomBar = listOf(
        BottomNavItem.Main.route,
        BottomNavItem.Schedule.route,
        BottomNavItem.Profile.route
    )

    val showBottomBar = currentDestination?.destination?.route in screensWithBottomBar

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomBar(navController = navController)
            }
        }
    ) { innerPadding ->
        MainNavigationHost(
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
            navController = navController,
        )
    }
}