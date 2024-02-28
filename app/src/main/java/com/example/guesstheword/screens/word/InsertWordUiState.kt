package com.example.guesstheword.screens.word

data class InsertWordUiState(
    val title : String = "",
    val existWord : Boolean = false,
    val insertedWord : Boolean = false
)