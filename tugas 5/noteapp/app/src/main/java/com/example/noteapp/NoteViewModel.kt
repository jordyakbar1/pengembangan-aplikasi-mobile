package com.example.noteapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class Note(
    val id: Int,
    var title: String,
    var content: String,
    var isFavorite: Boolean = false
)

class NoteViewModel : ViewModel() {
    private val _notes = mutableStateListOf<Note>()
    val notes: List<Note> get() = _notes

    private var nextId = 1

    fun addNote(title: String, content: String) {
        _notes.add(Note(id = nextId++, title = title, content = content))
    }

    fun updateNote(id: Int, title: String, content: String) {
        val index = _notes.indexOfFirst { it.id == id }
        if (index != -1) {
            _notes[index] = _notes[index].copy(title = title, content = content)
        }
    }

    fun toggleFavorite(id: Int) {
        val index = _notes.indexOfFirst { it.id == id }
        if (index != -1) {
            _notes[index] = _notes[index].copy(isFavorite = !_notes[index].isFavorite)
        }
    }

    fun getNoteById(id: Int): Note? {
        return _notes.find { it.id == id }
    }
}
