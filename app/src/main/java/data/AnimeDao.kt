package com.example.tp2_android.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {

    @Insert
    suspend fun insert(anime: AnimeEntity)

    @Update
    suspend fun update(anime: AnimeEntity)

    @Delete
    suspend fun delete(anime: AnimeEntity)

    @Query("SELECT * FROM anime_table")
    fun getAllAnime(): Flow<List<AnimeEntity>>

    @Query("SELECT * FROM anime_table WHERE isFavorite = 1")
    fun getFavoriteAnime(): Flow<List<AnimeEntity>>

    @Delete
    suspend fun deleteAnime(anime: AnimeEntity)
}