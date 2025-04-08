package olegator.shopinglist.main_screen



sealed class MainScreenEvent{
    object OnItemSave: MainScreenEvent()
    data class Navigate(val route: String): MainScreenEvent()
    data class NavigateMain(val route: String): MainScreenEvent()
    data class OnNewItemClick(val route: String): MainScreenEvent()

}
