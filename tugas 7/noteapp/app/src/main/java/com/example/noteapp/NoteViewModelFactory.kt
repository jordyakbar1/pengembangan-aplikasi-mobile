package com.example.noteapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.data.SettingsManager
import com.example.noteapp.db.NoteDatabase

class NoteViewModelFactory(
    private val database: NoteDatabase,
    private val settingsManager: SettingsManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(database, settingsManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
