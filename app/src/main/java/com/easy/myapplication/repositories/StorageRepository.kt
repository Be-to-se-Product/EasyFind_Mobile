package com.easy.myapplication.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class StorageRepository(
    private val dataStore: DataStore<Preferences>
){

    suspend fun saveToDataStore(key: String, value: String) {

        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

    suspend fun readFromDataStore(key: String): String? {
                 val preferences = dataStore.data.first()
                 preferences[stringPreferencesKey(key)]
                 return preferences[stringPreferencesKey(key)]

         }

    suspend fun clearDataStore() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}