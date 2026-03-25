package com.example.tp2_android.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime_table")
data class AnimeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val genre: String,
    val isFavorite: Boolean = false
)