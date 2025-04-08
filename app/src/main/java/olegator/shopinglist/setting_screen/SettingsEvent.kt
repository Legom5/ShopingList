package olegator.shopinglist.setting_screen

sealed class SettingsEvent{
    data class OnItemSelected(val color: String): SettingsEvent()

}
