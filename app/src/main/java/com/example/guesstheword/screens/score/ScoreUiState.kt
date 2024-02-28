package com.example.guesstheword.screens.score

import com.example.guesstheword.datamodel.Game

data class ScoreUiState (
    val isLoading : Boolean = true,
    val savedGame : Boolean = false,
    val games : List<Game> = emptyList(),
    val message : String? = null
)
