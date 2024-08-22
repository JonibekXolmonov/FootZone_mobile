package uz.mobile.footzone.android.navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import uz.mobile.footzone.android.R
import uz.mobile.footzone.android.presentation.screens.account.navigation.ACCOUNT_ROUTE
import uz.mobile.footzone.android.presentation.screens.main.navigation.MAIN_ROUTE
import uz.mobile.footzone.android.presentation.screens.schedule.navigation.SCHEDULE_ROUTE
import uz.mobile.footzone.android.theme.MyApplicationTheme
import uz.mobile.footzone.android.theme.blue100
import uz.mobile.footzone.android.theme.neutral10
import uz.mobile.footzone.android.theme.neutral100
import uz.mobile.footzone.android.theme.neutral60
import uz.mobile.footzone.android.theme.neutral80

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    items: List<BottomNavItem> = bottomTabs,
    navController: NavController
) {
    BottomNavigation(
        backgroundColor = neutral10,
        elevation = 0.dp,
        modifier = modifier
            .background(neutral10)
            .padding(bottom = 28.dp, top = 12.dp)
            .padding(),
    ) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
            BottomNavigationItem(
                icon = {
                    BottomBarTab(icon = screen.icon, isSelected = isSelected, label = screen.label)
                },
                alwaysShowLabel = false,
                selected = isSelected,
                selectedContentColor = Color.White,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun BottomBarTab(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    label: String,
    isSelected: Boolean = false,
    selectedContainerColor: Color = blue100,
    selectedIconColor: Color = neutral100,
    selectedLabelColor: Color = neutral100,
    unSelectedContainerColor: Color = neutral10,
    unSelectedIconColor: Color = neutral60,
    unSelectedLabelColor: Color = neutral80,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = modifier
                .clip(MaterialTheme.shapes.extraLarge)
                .background(if (isSelected) selectedContainerColor else unSelectedContainerColor)
                .padding(vertical = 4.dp, horizontal = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = icon), contentDescription = null,
                tint = if (isSelected) selectedIconColor else unSelectedIconColor
            )
        }

        Text(
            label, style = MaterialTheme.typography.labelMedium.copy(
                color = if (isSelected) selectedLabelColor else unSelectedLabelColor
            )
        )
    }
}

@Preview
@Composable
private fun BottomBarTabPr() {
    MyApplicationTheme {
        BottomBarTab(icon = R.drawable.stadium_icon, label = "Maydonlar")
    }
}

val bottomTabs = listOf(BottomNavItem.Main, BottomNavItem.Schedule, BottomNavItem.Profile)

sealed class BottomNavItem(val route: String, @DrawableRes val icon: Int, val label: String) {
    data object Main : BottomNavItem(MAIN_ROUTE, R.drawable.stadium_icon, "Maydonlar")
    data object Schedule : BottomNavItem(SCHEDULE_ROUTE, R.drawable.schedule, "Jadval")
    data object Profile : BottomNavItem(ACCOUNT_ROUTE, R.drawable.profile, "Akkaunt")
}