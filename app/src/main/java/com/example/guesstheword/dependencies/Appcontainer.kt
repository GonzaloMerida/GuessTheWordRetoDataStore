package com.example.guesstheword.dependencies

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.guesstheword.datamodel.UserPreferences
import com.example.guesstheword.datasource.LocalDatabase
import com.example.guesstheword.repositories.GamesRepository
import com.example.guesstheword.repositories.UserPreferencesRepository
import com.example.guesstheword.repositories.WordsRepository

//Datastore. Configuración básica de la app.
val Context.userDataStore by preferencesDataStore(name = UserPreferences.SETTINGS_FILE)

class Appcontainer(context : Context) {


    //Repositorio de palabras.
    private val _wordsRepository : WordsRepository by lazy {
        WordsRepository(LocalDatabase.getDatabase(context).wordsDao())
    }
    val wordsRepository get() = _wordsRepository

    //Repositorio de juegos.
    private val _gamesRepository : GamesRepository by lazy {
        GamesRepository(LocalDatabase.getDatabase(context).gamesDao())
    }
    val gamesRepository get() = _gamesRepository

    //Repositorio de configuración de usuario.
    private val _userPreferencesRepository : UserPreferencesRepository by lazy {
        UserPreferencesRepository(context.userDataStore)
    }
    val userPreferencesRepository get() = _userPreferencesRepository
}
