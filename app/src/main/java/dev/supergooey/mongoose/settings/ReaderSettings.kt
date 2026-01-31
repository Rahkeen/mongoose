package dev.supergooey.mongoose.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "reader_settings")

class ReaderSettings(private val context: Context) {
    private val readingDirectionKey = stringPreferencesKey("reading_direction")

    val readingDirection: Flow<ReadingDirection> = context.dataStore.data
        .map { preferences ->
            val directionString = preferences[readingDirectionKey] ?: ReadingDirection.Horizontal.name
            ReadingDirection.valueOf(directionString)
        }

    suspend fun setReadingDirection(direction: ReadingDirection) {
        context.dataStore.edit { preferences ->
            preferences[readingDirectionKey] = direction.name
        }
    }
}
