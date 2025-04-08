package olegator.shopinglist.main_screen

import olegator.shopinglist.R
import olegator.shopinglist.utils.Routes

sealed class BottomNavItem(val title: Int, val iconId: Int, val route: String){
    object ListItem: BottomNavItem(R.string.bti_list_item, R.drawable.list_icon, Routes.SHOPING_LIST )
    object NoteItem: BottomNavItem(R.string.bti_note_item, R.drawable.note_icon, Routes.NOTE_LIST )
    object AboutItem: BottomNavItem(R.string.bti_about_item, R.drawable.about_icon, Routes.ABOUT )
    object SettingsItem: BottomNavItem(R.string.bti_settings_item, R.drawable.settings_icon, Routes.SETTINGS )
}
