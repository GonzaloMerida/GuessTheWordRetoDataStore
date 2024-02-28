package com.example.guesstheword.screens.settings

data class SettingsUiState (
    val userName : String? = null,
    val difficulty : Int = 0,
    val saved: Boolean = false
)