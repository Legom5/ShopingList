package olegator.shopinglist.data


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_list_name")
data class ShopingListItem(
    @PrimaryKey
    val id: Int? = null,
    val name: String,
    val time: String,
    val allItemsCount: Int,
    val allSelectedItemsCount: Int
)
