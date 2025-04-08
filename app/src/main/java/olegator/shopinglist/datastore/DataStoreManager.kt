package olegator.shopinglist.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

const val DATA_STORE_NAME = "preference_storage_name"
private val Context.datatore: DataStore<Preferences> by preferencesDataStore(DATA_STORE_NAME)

class DataStoreManager(val context: Context) {

    suspend fun saveStringPreference(value: String, key: String) {
        context.datatore.edit { pref ->
            pref[stringPreferencesKey(key)] = value

        }
    }
    fun getStringPreference(key: String, defValue: String) = context.datatore.data.map { pref ->
        pref[stringPreferencesKey(key)] ?: defValue
    }

    companion object {
        const val TITLE_COLOR = "title_color"
    }
}