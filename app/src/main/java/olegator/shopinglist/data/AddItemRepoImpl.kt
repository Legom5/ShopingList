package olegator.shopinglist.data

import kotlinx.coroutines.flow.Flow

class AddItemRepoImpl(
    val dao: AddItemDao
): AddItemRepository {
    override suspend fun insertItem(item: AddItem) {
        dao.insertItem(item)
    }

    override suspend fun insertItem(item: ShopingListItem) {
        dao.insertItem(item)
    }

    override suspend fun deleteItem(item: AddItem) {
       dao.deleteItem(item)
    }

    override fun getAllItemsById(listId: Int): Flow<List<AddItem>> {
        return dao.getAllItemsById(listId)
    }

    override suspend fun getListItemById(listId: Int): ShopingListItem {
        return dao.getListItemById(listId)
    }
}