package com.example.guesstheword.dependencies

interface Factory<T> {
    fun create() : T
}