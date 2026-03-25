package com.example.tp2_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tp2_android.ui.theme.TP2_AndroidTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tp2_android.data.AnimeDatabase
import com.example.tp2_android.data.AnimeRepository
import com.example.tp2_android.viewModel.AnimeViewModel
import com.example.tp2_android.viewModel.AnimeViewModelFactory
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.tp2_android.data.AnimeEntity




/* val animeList = listOf(
    Anime(1, "Naruto", "Ninja - Action"),
    Anime(2, "Attack on Titan", "Dark Fantasy"),
    Anime(3, "One Piece", "Pirates - Adventure"),
    Anime(4, "Demon Slayer", "Sword - Action"),
    Anime(5, "Death Note", "Psychological"),
    Anime(6, "Dragon Ball", "Fighting")
) */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var isDarkMode by remember { mutableStateOf(false) }

            val database = AnimeDatabase.getDatabase(applicationContext)
            val repository = AnimeRepository(database.animeDao())
            val viewModel: AnimeViewModel = viewModel(
                factory = AnimeViewModelFactory(repository)
            )

            TP2_AndroidTheme(darkTheme = isDarkMode) {
                MainScreen(
                    isDarkMode = isDarkMode,
                    onToggleTheme = { isDarkMode = !isDarkMode },
                            viewModel = viewModel
                )
            }
        }
    }
}


@Composable
fun MainScreen(
    isDarkMode: Boolean,
    onToggleTheme: () -> Unit,
    viewModel: AnimeViewModel
) {
    val allAnime by viewModel.allAnime.collectAsState(initial = emptyList())
    val favoriteAnime by viewModel.favoriteAnime.collectAsState(initial = emptyList())

    var showFavoritesOnly by remember { mutableStateOf(false) }

    val animeList = if (showFavoritesOnly) favoriteAnime else allAnime

    var title by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var selectedAnime by remember { mutableStateOf<AnimeEntity?>(null) }

    var counter by remember { mutableStateOf(0) }
    var showText by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Header
        Text(
            text = "Anime Application",
            style = MaterialTheme.typography.headlineMedium
        )

        Switch(
            checked = isDarkMode,
            onCheckedChange = { onToggleTheme() }
        )

        Button(
            onClick = { showFavoritesOnly = !showFavoritesOnly }
        ) {
            Text(if (showFavoritesOnly) "Afficher tous" else "Afficher favoris")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Titre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = genre,
            onValueChange = { genre = it },
            label = { Text("Genre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (title.isNotBlank() && genre.isNotBlank()) {

                    if (selectedAnime == null) {
                        // CREATE
                        viewModel.insertAnime(title, genre)
                    } else {
                        // UPDATE
                        viewModel.updateAnime(
                            selectedAnime!!.copy(
                                title = title,
                                genre = genre
                            )
                        )
                        selectedAnime = null
                    }

                    title = ""
                    genre = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (selectedAnime == null) "Ajouter" else "Modifier")
        }

        // Row avec 2 boutons
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {

            Button(onClick = { counter++ }) {
                Text("+1")
            }

            Button(onClick = { showText = !showText }) {
                Text("Afficher/Masquer")
            }
        }

        Text("Compteur: $counter")

        if (showText) {
            Text("Bienvenue dans mon app Anime!")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // LazyColumn
        LazyColumn {
            items(animeList) { anime ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            selectedAnime = anime
                            title = anime.title
                            genre = anime.genre
                        }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            text = anime.title,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = anime.genre,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = { viewModel.deleteAnime(anime) }
                        ) {
                            Text("Supprimer")
                        }

                        Button(
                            onClick = { viewModel.toggleFavorite(anime) }
                        ) {
                            Text(if (anime.isFavorite) "Retirer ⭐" else "Favori ⭐")
                        }
                    }
                }
            }
        }
    }
}