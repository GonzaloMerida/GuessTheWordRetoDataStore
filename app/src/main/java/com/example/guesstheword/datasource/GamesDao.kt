package com.example.guesstheword.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.guesstheword.datamodel.Game
import kotlinx.coroutines.flow.Flow

@Dao
interface GamesDao {

    @Insert
    suspend fun insertGame(game: Game)

    @Delete
    suspend fun deleteGame(game: Game)

    @Query("DELETE FROM games")
    suspend fun clearGames()

    @Query("SELECT * FROM games ORDER BY date DESC")
    fun gelAllGames() : Flow<List<Game>>

}