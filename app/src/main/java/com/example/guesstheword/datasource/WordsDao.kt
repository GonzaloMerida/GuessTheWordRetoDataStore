package com.example.guesstheword.datasource

import androidx.room.*
import com.example.guesstheword.datamodel.Word

@Dao
interface WordsDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertWord(word: Word)

    @Delete
    suspend fun deleteWord(word : Word)

    @Query("SELECT * FROM words ORDER BY RANDOM()")
    suspend fun getAllWords() : List<Word>

    @Query("SELECT * FROM words ORDER BY RANDOM() DESC LIMIT 1")
    suspend fun getRandomWord(): Word?

    @Query("DELETE FROM words")
    suspend fun clearWords()

    @Query("SELECT * FROM words WHERE title LIKE :title")
    suspend fun getWord(title : String) : Word?

    @Query("SELECT * FROM words ORDER BY RANDOM() ASC LIMIT :max")
    suspend fun getSomeRandomWords(max : Int) : List<Word>

}