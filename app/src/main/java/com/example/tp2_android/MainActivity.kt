package com.example.tp2_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tp2_android.ui.theme.TP2_AndroidTheme




val animeList = listOf(
    Anime(1, "Naruto", "Ninja - Action"),
    Anime(2, "Attack on Titan", "Dark Fantasy"),
    Anime(3, "One Piece", "Pirates - Adventure"),
    Anime(4, "Demon Slayer", "Sword - Action"),
    Anime(5, "Death Note", "Psychological"),
    Anime(6, "Dragon Ball", "Fighting")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var isDarkMode by remember { mutableStateOf(false) }

            TP2_AndroidTheme(darkTheme = isDarkMode) {
                MainScreen(
                    isDarkMode = isDarkMode,
                    onToggleTheme = { isDarkMode = !isDarkMode }
                )
            }
        }
    }
}


@Composable
fun MainScreen(
    isDarkMode: Boolean,
    onToggleTheme: () -> Unit
) {
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

        Spacer(modifier = Modifier.height(16.dp))

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
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(anime.title, style = MaterialTheme.typography.titleMedium)
                        Text(anime.subtitle)
                    }
                }
            }
        }
    }
}