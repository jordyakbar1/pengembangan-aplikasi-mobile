package com.example.noteapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Summarize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.noteapp.NoteViewModel
import com.example.noteapp.db.NoteEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    noteId: Long,
    viewModel: NoteViewModel,
    onBack: () -> Unit,
    onEditClick: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var note by remember { mutableStateOf<NoteEntity?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    
    var showSummarizationResult by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(noteId) {
        note = viewModel.getNoteById(noteId)
        isLoading = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Note Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (note != null) {
                        IconButton(onClick = { 
                            viewModel.summarizeNote(note!!.content) { summary ->
                                showSummarizationResult = summary
                            }
                        }) {
                            Icon(Icons.Default.Summarize, contentDescription = "Summarize")
                        }
                        IconButton(onClick = { onEditClick(noteId) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when {
                isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                note != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = note!!.title,
                            style = MaterialTheme.typography.headlineLarge
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = note!!.content,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Note not found")
                    }
                }
            }

            if (uiState.isSummarizing) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Summarizing note...")
                    }
                }
            }
        }
    }

    // Summarization Result Dialog
    if (showSummarizationResult != null) {
        AlertDialog(
            onDismissRequest = { showSummarizationResult = null },
            title = { Text("Summary Result") },
            text = {
                Text(showSummarizationResult!!)
            },
            confirmButton = {
                TextButton(onClick = {
                    val currentNote = note
                    if (currentNote != null) {
                        viewModel.updateNote(currentNote.id, currentNote.title, showSummarizationResult!!)
                        note = currentNote.copy(content = showSummarizationResult!!)
                    }
                    showSummarizationResult = null
                }) {
                    Text("Replace content with summary")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSummarizationResult = null }) {
                    Text("Close")
                }
            }
        )
    }

    // Error Dialog
    uiState.summarizationError?.let { error ->
        AlertDialog(
            onDismissRequest = { viewModel.clearSummarizationError() },
            title = { Text("Summarization Error") },
            text = { Text(error) },
            confirmButton = {
                TextButton(onClick = { viewModel.clearSummarizationError() }) {
                    Text("OK")
                }
            }
        )
    }
}
