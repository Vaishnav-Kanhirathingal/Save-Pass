package com.kenetic.savepass.password.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

private const val USER_MASTER_PASSWORD = "user_password"
private val Context.datastore: DataStore<Preferences> by preferencesDataStore(
    name = USER_MASTER_PASSWORD
)

class AppDataStore(context: Context) {
    private val CURRENT_USER_MASTER_PASSWORD = stringPreferencesKey("current_user_password")

    val userMasterPasswordFlow: Flow<String> = context.datastore.data.catch {
        if (it is IOException) {
            it.printStackTrace()
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { preferences ->
        preferences[CURRENT_USER_MASTER_PASSWORD] ?: ""
    }

    fun editMasterPassword(masterPass: String, context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            context.datastore.edit { preferences ->
                preferences[CURRENT_USER_MASTER_PASSWORD] = masterPass
            }
        }
    }
}