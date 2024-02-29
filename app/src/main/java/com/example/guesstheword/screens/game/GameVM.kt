package com.example.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.guesstheword.datamodel.UserPreferences
import com.example.guesstheword.dependencies.MyApplication
import com.example.guesstheword.repositories.UserPreferencesRepository
import com.example.guesstheword.repositories.WordsRepository
import com.example.guesstheword.utils.MyTimer
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.math.floor

class GameVM(
    val wordsRepository: WordsRepository,
    val settingsRepository: UserPreferencesRepository
    ) : ViewModel() {

    //Flujo de estado.
    private val _uiState : MutableStateFlow<GameUiState> = MutableStateFlow(GameUiState())
    val uiState : StateFlow<GameUiState> = _uiState.asStateFlow()

    private var userName : String = ""
    private var dif : Int = 1

    private var _num_words = 10
        get() = _num_words

    init {
        //inicializa las palabras.
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.getUserPrefs().collect(){userPreferences ->
                userName = userPreferences.name
                dif = userPreferences.level
            }

            _num_words = when(dif){
                UserPreferences.LOW -> 4
                UserPreferences.MEDIUM -> 6
                else -> 8
            }

            val words = wordsRepository.getSomeRandomWords(NUM_WORDS)
            _uiState.update { currentSate ->
                currentSate.copy(
                    word = if(words.isEmpty()) null else words[0],
                    score = 0,
                    wordList = if (words.size > 1) words.subList(1,words.size) else emptyList(),
                    noMoreWords = words.isEmpty(),
                    message = if(words.isEmpty()) "No hay palabras." else null,
                    userName = userName,
                    difficulty = dif
                )
            }
        }
        //inicializa el temporizador.
        viewModelScope.launch {
            MyTimer(10f,0.5f,500).timer.filter {
                it - floor(it) == 0f
            }.collect {
                _uiState.update { currentState ->
                    currentState.copy(
                        time = it
                    )
                }
            }
        }
    }

    //Función que toma una nueva palabra e incrementa o decrementa la puntuación.
    fun nextWord(acierto : Boolean) {
        val inc = if (acierto) 1 else -1
        //si palabra no es nula
        uiState.value.word?.let {
            _uiState.update { currentState ->
                currentState.copy(
                    //si la lista está vacia la próxima palabra es nula.
                    word = if (currentState.wordList.isEmpty()) null else currentState.wordList[0],
                    score = currentState.score+inc,
                    wordList = if(currentState.wordList.size > 1) currentState.wordList.subList(1,currentState.wordList.size) else emptyList(),
                    rightWords = if (acierto)
                        currentState.rightWords.plus(currentState.word!!.title)
                    else
                        currentState.rightWords,
                    wrongWords = if (!acierto)
                        currentState.wrongWords.plus(currentState.word!!.title)
                    else
                        currentState.wrongWords,
                    //si la lista vacía, no hay mas palabras (flag).
                    noMoreWords = currentState.wordList.isEmpty()
                )
            }

        }
        //mostrar mensaje por pantalla cuando la puntuación llegue a 10
        if(uiState.value.score == 10 && inc == 1)
            _uiState.update { currenState ->
                currenState.copy(
                    message = "Good score!"
                )
            }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destruido!")
    }

    fun messageShown() {
        _uiState.update { currenState ->
            currenState.copy(
                message = null
            )
        }
    }

    fun disableButtonsCompleted() {
        _uiState.update {
            it.copy(
                noMoreWords = false
            )
        }
    }


    companion object {
        //número máximo de palabras del juego.
        val NUM_WORDS = 10

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])

                return GameVM(
                    (application as MyApplication).appcontainer.wordsRepository
                ) as T
            }
        }
    }
}