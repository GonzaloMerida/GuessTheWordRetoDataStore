package com.example.guesstheword.screens.settings

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.guesstheword.datamodel.UserPreferences
import com.example.guesstheword.dependencies.*
import com.example.guesstheword.repositories.UserPreferencesRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SettingsVM(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState : MutableStateFlow<UserPreferences> = MutableStateFlow(UserPreferences())
    val uiState : StateFlow<UserPreferences> = _uiState.asStateFlow()

    init {
        //al iniciar toma el estado desde el DataStore.
        viewModelScope.launch {
            updateState()
        }
    }

    //actualiza el flujo de estado con los datos de DataStore.
    private suspend fun updateState() {
        userPreferencesRepository.getUserPrefs().collect { userPrefFlow ->
            _uiState.update { currentState ->
                userPrefFlow.copy()
            }
        }
    }

    fun saveSettings(name: String, level : Int) {
        Log.d("datastore","nombre: $name, nivel: $level")
        viewModelScope.launch {
            //edita el DataStore.
            userPreferencesRepository.saveSettings(name,level)
            //actualiza el flujo de estado para que persista en pantalla.
            updateState()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return SettingsVM(
                    (application as MyApplication).appcontainer.userPreferencesRepository
                ) as T
            }
        }
    }
}