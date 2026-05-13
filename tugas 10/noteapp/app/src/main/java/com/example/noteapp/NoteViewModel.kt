package com.example.noteapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.AppTheme
import com.example.noteapp.data.NoteRepository
import com.example.noteapp.data.SettingsManager
import com.example.noteapp.data.SortOrder
import com.example.noteapp.data.SummarizationService
import com.example.noteapp.db.NoteEntity
import com.example.noteapp.platform.DeviceInfo
import com.example.noteapp.platform.NetworkMonitor
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class NotesUiState(
    val notes: List<NoteEntity> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val sortOrder: SortOrder = SortOrder.BY_DATE,
    val theme: AppTheme = AppTheme.SYSTEM,
    val isOnline: Boolean = false,
    val deviceInfo: DeviceInfo? = null,
    val isSummarizing: Boolean = false,
    val summarizationError: String? = null
)

class NoteViewModel(
    private val repository: NoteRepository,
    private val settingsManager: SettingsManager,
    private val networkMonitor: NetworkMonitor,
    private val summarizationService: SummarizationService,
    val deviceInfo: DeviceInfo
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotesUiState(isLoading = true, deviceInfo = deviceInfo))
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    private val queryFlow = MutableStateFlow("")

    init {
        observeSettings()
        observeNotes()
        observeNetwork()
    }

    private fun observeNetwork() {
        networkMonitor.isOnline.onEach { isOnline ->
            _uiState.update { it.copy(isOnline = isOnline) }
        }.launchIn(viewModelScope)
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
        }.flatMapLatest { (query, sortOrder) ->
            _uiState.update { it.copy(isLoading = true, searchQuery = query, sortOrder = sortOrder) }
            if (query.isEmpty()) {
                repository.getAllNotes()
            } else {
                repository.searchNotes(query)
            }.map { notes ->
                val sortedNotes = when (sortOrder) {
                    SortOrder.BY_DATE -> notes.sortedByDescending { it.createdAt }
                    SortOrder.BY_TITLE -> notes.sortedBy { it.title.lowercase() }
                }
                sortedNotes
            }
        }.onEach { notes ->
            _uiState.update { it.copy(notes = notes, isLoading = false) }
        }.launchIn(viewModelScope)
    }

    fun onSearchQueryChange(query: String) {
        queryFlow.value = query
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            repository.insertNote(null, title, content)
        }
    }

    fun updateNote(id: Long, title: String, content: String) {
        viewModelScope.launch {
            repository.insertNote(id, title, content)
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            repository.deleteNote(id)
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
        return repository.getNoteById(id)
    }

    fun summarizeNote(content: String, onResult: (String) -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSummarizing = true, summarizationError = null) }
            val result = summarizationService.summarize(content)
            result.onSuccess { summary ->
                _uiState.update { it.copy(isSummarizing = false) }
                onResult(summary)
            }.onFailure { error ->
                _uiState.update { it.copy(isSummarizing = false, summarizationError = error.message) }
            }
        }
    }

    fun clearSummarizationError() {
        _uiState.update { it.copy(summarizationError = null) }
    }
}
