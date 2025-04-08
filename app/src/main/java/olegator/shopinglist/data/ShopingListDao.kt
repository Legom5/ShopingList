package olegator.shopinglist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow


@Dao
interface ShopingListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ShopingListItem)
    @Delete
    suspend fun deleteItem(item: ShopingListItem)
    @Query("DELETE FROM add_item WHERE listId = :listId")
    suspend fun  deleteAddItems(listId: Int)
    @Query("SELECT * FROM shop_list_name")
    fun getAllItems(): Flow<List<ShopingListItem>>
    @Transaction
    suspend fun deleteShopingList(item: ShopingListItem){
        deleteAddItems(item.id!!)
        deleteItem(item)
    }

}