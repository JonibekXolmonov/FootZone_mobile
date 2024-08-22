package uz.mobile.footzone.android.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uz.mobile.footzone.android.navigation.BottomBar
import uz.mobile.footzone.android.navigation.MainNavigationHost

@Composable
fun FootZoneApp(
    navController: NavHostController = rememberNavController(),
) {

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { innerPadding ->
        MainNavigationHost(
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
            navController = navController,
        )
    }
}