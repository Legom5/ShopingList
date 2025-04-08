package olegator.shopinglist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import olegator.shopinglist.about_screen.AboutScreen
import olegator.shopinglist.add_item_screen.AddItemScreen
import olegator.shopinglist.main_screen.MainScreen
import olegator.shopinglist.new_note_screen.NewNoteScreen
import olegator.shopinglist.note_list_screen.NoteListScreen
import olegator.shopinglist.setting_screen.SettingsScreen
import olegator.shopinglist.shoping_list_screen.ShopingListScreen
import olegator.shopinglist.utils.Routes

@Composable
fun MainNavigationGraph() {
    val navContoller = rememberNavController()


    NavHost(navController =navContoller, startDestination = Routes.MAIN_SCREEN){
        composable(Routes.ADD_ITEM + "/{listId}") {
            AddItemScreen()
        }
        composable(Routes.NEW_NOTE + "/{noteId}") {
            NewNoteScreen(){
                navContoller.popBackStack()
            }
        }
        composable(Routes.MAIN_SCREEN) {
            MainScreen(navContoller)
        }

    }
}