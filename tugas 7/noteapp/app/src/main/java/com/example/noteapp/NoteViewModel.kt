package com.example.noteapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.AppTheme
import com.example.noteapp.data.SettingsManager
import com.example.noteapp.data.SortOrder
import com.example.noteapp.db.NoteDatabase
import com.example.noteapp.db.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class NotesUiState(
    val notes: List<NoteEntity> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val sortOrder: SortOrder = SortOrder.BY_DATE,
    val theme: AppTheme = AppTheme.SYSTEM
)

class NoteViewModel(
    private val database: NoteDatabase,
    private val settingsManager: SettingsManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotesUiState(isLoading = true))
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    private val queryFlow = MutableStateFlow("")

    init {
        observeSettings()
        observeNotes()
    }

    private fun observeSettings() {
        settingsManager.theme.onEach { theme ->
            _uiState.update { it.copy(theme = theme) }
        }.launchIn(viewModelScope)
    }

    private fun observeNotes() {
        combine(
            queryFlow,
            settingsManager.sortOrder
        ) { query, sortOrder ->
            Pair(query, sortOrder)
        }.onEach { (query, sortOrder) ->
            _uiState.update { it.copy(isLoading = true, searchQuery = query, sortOrder = sortOrder) }
            
            val notes = withContext(Dispatchers.IO) {
                if (query.isEmpty()) {
                    database.noteQueries.selectAll().executeAsList()
                } else {
                    database.noteQueries.searchNotes(query).executeAsList()
                }
            }
            
            val sortedNotes = when (sortOrder) {
                SortOrder.BY_DATE -> notes.sortedByDescending { it.createdAt }
                SortOrder.BY_TITLE -> notes.sortedBy { it.title.lowercase() }
            }
            
            _uiState.update { it.copy(notes = sortedNotes, isLoading = false) }
        }.launchIn(viewModelScope)
    }

    fun onSearchQueryChange(query: String) {
        queryFlow.value = query
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database.noteQueries.insertNote(
                    id = null,
                    title = title,
                    content = content,
                    createdAt = System.currentTimeMillis()
                )
            }
            // Refresh notes after adding
            onSearchQueryChange(queryFlow.value)
        }
    }

    fun updateNote(id: Long, title: String, content: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val existing = database.noteQueries.getNoteById(id).executeAsOne()
                database.noteQueries.insertNote(
                    id = id,
                    title = title,
                    content = content,
                    createdAt = existing.createdAt
                )
            }
            onSearchQueryChange(queryFlow.value)
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database.noteQueries.deleteNote(id)
            }
            onSearchQueryChange(queryFlow.value)
        }
    }

    fun updateSortOrder(order: SortOrder) {
        viewModelScope.launch {
            settingsManager.updateSortOrder(order)
        }
    }

    fun updateTheme(theme: AppTheme) {
        viewModelScope.launch {
            settingsManager.updateTheme(theme)
        }
    }
    
    suspend fun getNoteById(id: Long): NoteEntity? {
        return withContext(Dispatchers.IO) {
            database.noteQueries.getNoteById(id).executeAsOneOrNull()
        }
    }
}
