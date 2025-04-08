package olegator.shopinglist.data


import kotlinx.coroutines.flow.Flow

interface ShopingListRepository {
    suspend fun insertItem(item: ShopingListItem)
    suspend fun deleteItem(item: ShopingListItem)
    fun getAllItems(): Flow<List<ShopingListItem>>

}