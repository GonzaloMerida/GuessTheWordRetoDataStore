package com.example.guesstheword.datamodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("games")
data class Game(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    @ColumnInfo(name = "date")
    val date : String = "",
    @ColumnInfo(name = "score")
    val score : Int = 0,
    @ColumnInfo(name = "name")
    val name : String = "",
    @ColumnInfo(name = "right_words")
    val rightWords : String = "",
    @ColumnInfo(name = "wrong_words")
    val wrongWords : String = ""
)