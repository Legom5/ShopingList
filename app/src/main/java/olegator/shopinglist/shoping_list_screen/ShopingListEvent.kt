package olegator.shopinglist.shoping_list_screen

import olegator.shopinglist.data.ShopingListItem

sealed class ShopingListEvent{
    data class OnShowDeleteDialog(val item: ShopingListItem): ShopingListEvent()
    data class OnShowEditDialog(val item: ShopingListItem): ShopingListEvent()
    data class OnItemClick(val route: String): ShopingListEvent()
    object OnItemSave: ShopingListEvent()
}
