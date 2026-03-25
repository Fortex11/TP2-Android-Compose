package com.example.tp2_android.data

import kotlinx.coroutines.flow.Flow

class AnimeRepository(private val animeDao: AnimeDao) {

    fun getFavoriteAnime() = animeDao.getFavoriteAnime()

    val allAnime: Flow<List<AnimeEntity>> = animeDao.getAllAnime()

    suspend fun insert(anime: AnimeEntity) {
        animeDao.insert(anime)
    }

    suspend fun update(anime: AnimeEntity) {
        animeDao.update(anime)
    }

    suspend fun delete(anime: AnimeEntity) {
        animeDao.delete(anime)
    }

    suspend fun deleteAnime(anime: AnimeEntity) {
        animeDao.deleteAnime(anime)
    }
}