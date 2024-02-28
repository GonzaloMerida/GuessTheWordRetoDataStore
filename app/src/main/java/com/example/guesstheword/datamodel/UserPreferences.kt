package com.example.guesstheword.datamodel

data class UserPreferences (
    val name : String = "",
    val level : Int = MEDIUM
    ) {
    //valores constantes auxiliares para las preferencias de usuario.
    companion object {
        const val SETTINGS_FILE = "settings"
        const val USER_NAME = "username"
        const val LEVEL = "level"
        const val ANONYMOUS = "Anonymous"
        const val LOW = 0
        const val MEDIUM = 1
        const val HIGH = 2
    }
}