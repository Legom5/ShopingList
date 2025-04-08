package olegator.shopinglist.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ShopingListItem::class, AddItem::class, NoteItem::class],
    version = 1,
    exportSchema = true
)
abstract class MainDb : RoomDatabase() {
    abstract val shopingListDao: ShopingListDao
    abstract val noteDao: NoteDao
    abstract val addItemtDao: AddItemDao
}