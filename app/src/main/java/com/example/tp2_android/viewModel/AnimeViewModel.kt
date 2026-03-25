package com.example.tp2_android.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tp2_android.data.AnimeEntity
import com.example.tp2_android.data.AnimeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AnimeViewModel(private val repository: AnimeRepository) : ViewModel() {

    val allAnime: StateFlow<List<AnimeEntity>> = repository.allAnime
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoriteAnime: StateFlow<List<AnimeEntity>> = repository.getFavoriteAnime()
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    fun insertAnime(title: String, genre: String) {
        viewModelScope.launch {
            repository.insert(
                AnimeEntity(
                    title = title,
                    genre = genre
                )
            )
        }
    }

    fun updateAnime(anime: AnimeEntity) {
        viewModelScope.launch {
            repository.update(anime)
        }
    }

   /* fun deleteAnime(anime: AnimeEntity) {
        viewModelScope.launch {
            repository.delete(anime)
        }
    } */

    fun toggleFavorite(anime: AnimeEntity) {
        viewModelScope.launch {
            repository.update(
                anime.copy(isFavorite = !anime.isFavorite)
            )
        }
    }

    fun deleteAnime(anime: AnimeEntity) {
        viewModelScope.launch {
            repository.deleteAnime(anime)
        }
    }
}

class AnimeViewModelFactory(private val repository: AnimeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnimeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}