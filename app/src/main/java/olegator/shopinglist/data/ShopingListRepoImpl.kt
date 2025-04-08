package olegator.shopinglist.data

import kotlinx.coroutines.flow.Flow

class ShopingListRepoImpl(
    private val dao: ShopingListDao
) : ShopingListRepository {
    override suspend fun insertItem(item: ShopingListItem) {
        dao.insertItem(item)
    }

    override suspend fun deleteItem(item: ShopingListItem) {
       dao.deleteShopingList(item)
    }

    override fun getAllItems(): Flow<List<ShopingListItem>> {
        return dao.getAllItems()
    }
}