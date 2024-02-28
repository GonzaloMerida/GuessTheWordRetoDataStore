package com.example.guesstheword.screens.score

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.guesstheword.datamodel.Game
import com.example.guesstheword.dependencies.MyApplication
import com.example.guesstheword.repositories.GamesRepository
import com.example.guesstheword.screens.game.GameUiState
import com.example.guesstheword.screens.game.GameVM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ScoreViewModel(
    private val gamesRepository: GamesRepository
) : ViewModel() {
    // The final score
    private var _finalScore : Int = 0
    val finalScore get() = _finalScore

    private var _rightWords : String = ""
    val rightWords get() = _rightWords

    private var _wrongWords : String = ""
    val wrongWords get() = _wrongWords

    private val _uiState : MutableStateFlow<ScoreUiState> = MutableStateFlow(ScoreUiState())
    val uiState : StateFlow<ScoreUiState> = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            gamesRepository.getAllGames().collect { games ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        games = games
                    )
                }
            }
        }
    }

    fun setFinalScore(score : Int) {
        _finalScore = score
    }

    fun setRightWords(rightWords: String) {
        _rightWords = rightWords
    }

    fun setWrongWords(wrongWords: String) {
        _wrongWords = wrongWords
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveGame(name : String) {
        viewModelScope.launch {
            try {
                gamesRepository.insertGame(
                    Game(date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")),
                        score = finalScore,
                        name = name,
                        rightWords = rightWords,
                        wrongWords = wrongWords
                        )
                )
                _uiState.update {
                    it.copy(
                        savedGame = true
                    )
                }
            } catch (exception : Exception) {
                _uiState.update {
                    it.copy(
                        message = "No se ha podido guardar el juego."
                    )
                }
            }
        }
    }

    fun savedGameCompleted() {
        _uiState.update {
            it.copy(
                savedGame = false
            )
        }
    }

    fun messageShown() {
        _uiState.update {
            it.copy(
                message = null
            )
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

                return ScoreViewModel(
                    (application as MyApplication).appcontainer.gamesRepository
                ) as T
            }
        }
    }
}