package com.example.guesstheword.repositories

import com.example.guesstheword.datamodel.Game
import com.example.guesstheword.datasource.GamesDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GamesRepository (
    private val gamesDao: GamesDao,
    private val ioDispatcher : CoroutineDispatcher = Dispatchers.IO
        ) {

    suspend fun insertGame(game: Game) = withContext(ioDispatcher) {
        gamesDao.insertGame(game)
    }

    suspend fun deleteGame(game: Game) = withContext(ioDispatcher) {
        gamesDao.deleteGame(game)
    }

    suspend fun clearGames() = withContext(ioDispatcher) {
        gamesDao.clearGames()
    }

    fun getAllGames() : Flow<List<Game>> {
        return gamesDao.gelAllGames()
    }

}