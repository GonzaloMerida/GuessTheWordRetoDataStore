package com.example.guesstheword.repositories

import android.util.Log
import androidx.room.Query
import com.example.guesstheword.datamodel.Word
import com.example.guesstheword.datasource.WordsDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class WordsRepository (
    private val wordsDao: WordsDao,
    private val ioDispatcher : CoroutineDispatcher = Dispatchers.IO
        ) {

    suspend fun insertWord(word: Word) = withContext(ioDispatcher) {
        wordsDao.insertWord(word)
    }

    suspend fun getAllWords() : List<Word> = withContext(ioDispatcher) {
         return@withContext wordsDao.getAllWords()
    }

    suspend fun deleteWord(word : Word) = withContext(ioDispatcher) {
        wordsDao.deleteWord(word)
    }

    suspend fun getRandomWord(): Word? = withContext(ioDispatcher) {
        return@withContext wordsDao.getRandomWord()
    }

    suspend fun clearWords() = withContext(ioDispatcher) {
        wordsDao.clearWords()
    }

    suspend fun getWord(title : String) = withContext(ioDispatcher) {
         return@withContext wordsDao.getWord(title)
    }

    suspend fun getSomeRandomWords(max : Int) : List<Word> = withContext(ioDispatcher) {
        Log.d("flows", "entra 2")
        return@withContext wordsDao.getSomeRandomWords(max)
    }

}
