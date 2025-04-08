package olegator.shopinglist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import olegator.shopinglist.about_screen.AboutScreen
import olegator.shopinglist.note_list_screen.NoteListScreen
import olegator.shopinglist.setting_screen.SettingsScreen
import olegator.shopinglist.shoping_list_screen.ShopingListScreen
import olegator.shopinglist.utils.Routes

@Composable
fun NavigationGraph(navContoller: NavHostController, onNavigate: (String) -> Unit) {


    NavHost(navController = navContoller, startDestination = Routes.SHOPING_LIST) {
        composable(Routes.SHOPING_LIST) {
            ShopingListScreen() { route ->
                onNavigate(route)

            }
        }
        composable(Routes.NOTE_LIST) {
            NoteListScreen() { route ->
                onNavigate(route)
            }
        }
        composable(Routes.ABOUT) {
            AboutScreen()
        }
        composable(Routes.SETTINGS) {
            SettingsScreen()
        }
    }
}