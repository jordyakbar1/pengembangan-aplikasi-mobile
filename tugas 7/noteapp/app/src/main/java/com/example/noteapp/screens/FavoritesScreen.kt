package com.example.noteapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.noteapp.NoteViewModel

@Composable
fun FavoritesScreen(viewModel: NoteViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    // Note: 'isFavorite' is not yet implemented in the database schema.
    // Showing an empty list or filtering if it existed.
    val favoriteNotes = emptyList<com.example.noteapp.db.NoteEntity>() 

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Favorite Notes",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (favoriteNotes.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No favorite notes yet.")
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(favoriteNotes, key = { it.id }) { note ->
                    NoteItem(
                        note = note,
                        onClick = { /* Navigate to detail */ }
                    )
                }
            }
        }
    }
}
