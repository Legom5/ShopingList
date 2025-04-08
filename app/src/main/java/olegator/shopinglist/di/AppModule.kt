package olegator.shopinglist.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import olegator.shopinglist.data.AddItemRepoImpl
import olegator.shopinglist.data.AddItemRepository
import olegator.shopinglist.data.MainDb
import olegator.shopinglist.data.NoteRepoImpl
import olegator.shopinglist.data.NoteRepository
import olegator.shopinglist.data.ShopingListRepoImpl
import olegator.shopinglist.data.ShopingListRepository
import olegator.shopinglist.datastore.DataStoreManager
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMainDb(app: Application): MainDb{
        return Room.databaseBuilder(
            app,
            MainDb::class.java,
            "shop_list_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideShopRepo(db: MainDb): ShopingListRepository{
        return ShopingListRepoImpl(db.shopingListDao)
    }

    @Provides
    @Singleton
    fun provideAddItemRepo(db: MainDb): AddItemRepository{
        return AddItemRepoImpl(db.addItemtDao)
    }

    @Provides
    @Singleton
    fun provideNoteRepo(db: MainDb): NoteRepository{
        return NoteRepoImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideDataStoreManager(app: Application): DataStoreManager{
        return DataStoreManager(app)
    }
}