package olegator.shopinglist.main_screen

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import olegator.shopinglist.ui.theme.BlueLight

@Composable
fun BottomNav(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val litItems = listOf(
        BottomNavItem.ListItem,
        BottomNavItem.NoteItem,
        BottomNavItem.AboutItem,
        BottomNavItem.SettingsItem
    )
    BottomNavigation(backgroundColor = Color.White) {
        litItems.forEach { bottomNavItem ->

            BottomNavigationItem(
                selected = currentRoute == bottomNavItem.route,
                onClick = {
                    onNavigate(bottomNavItem.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = bottomNavItem.iconId),
                        contentDescription = "icon"
                    )
                },
                label = {
                    Text(text = stringResource(bottomNavItem.title))
                },
                selectedContentColor = BlueLight,
                unselectedContentColor = Color.Gray,
                alwaysShowLabel = false
            )

        }
    }
}