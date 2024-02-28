package com.example.guesstheword.repositories

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.viewModelScope
import com.example.guesstheword.datamodel.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class UserPreferencesRepository (
    private val userDataStore: DataStore<Preferences>
        ){

    //toma los datos de DataStore. Devuelve un flujo.
    fun getUserPrefs() : Flow<UserPreferences> {
        return userDataStore.data.map { userPreferences ->
            val name = userPreferences[stringPreferencesKey(UserPreferences.USER_NAME)]
                ?: UserPreferences.ANONYMOUS
            val intLevel = userPreferences[intPreferencesKey(UserPreferences.LEVEL)] ?: 1
             return@map UserPreferences(
                name = name,
                level = intLevel
            )
        }
    }

    //Escribe los datos en el Datastore.
    suspend fun saveSettings(name: String, level : Int) {
        //edita el DataStore.
        userDataStore.edit { userPreferences ->
            userPreferences[stringPreferencesKey(UserPreferences.USER_NAME)] = name
            userPreferences[intPreferencesKey(UserPreferences.LEVEL)] = level
        }
    }

}