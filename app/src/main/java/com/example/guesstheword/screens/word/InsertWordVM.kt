package com.example.guesstheword.screens.word

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.guesstheword.datamodel.Word
import com.example.guesstheword.dependencies.MyApplication
import com.example.guesstheword.repositories.WordsRepository
import com.example.guesstheword.screens.game.GameVM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InsertWordVM(
    private val wordsRepository: WordsRepository
) : ViewModel() {

    private val _insertWordUiState : MutableStateFlow<InsertWordUiState> = MutableStateFlow(
        InsertWordUiState()
    )
    val insertWordUiState : StateFlow<InsertWordUiState> = _insertWordUiState.asStateFlow()

    fun insertWord(title: String) {

        viewModelScope.launch {
            val word = wordsRepository.getWord(title)
            if(word == null) {
                wordsRepository.insertWord(Word(title = title))
                _insertWordUiState.update { currentState ->
                    currentState.copy(
                        title = title,
                        existWord = false,
                        insertedWord = true
                    )
                }
            } else {
                _insertWordUiState.update { currentState ->
                    currentState.copy(
                        title = title,
                        existWord = true,
                        insertedWord = false
                    )
                }
            }
        }
    }

    fun messageShown() {
        _insertWordUiState.update { currentState ->
            currentState.copy(
                title = "",
                existWord = false,
                insertedWord = false
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

                return InsertWordVM(
                    (application as MyApplication).appcontainer.wordsRepository
                ) as T
            }
        }
    }
}