package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.myapplication.ui.viewmodel.ProfileUiState


class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    fun updateName(newName: String) {
        _uiState.value = _uiState.value.copy(name = newName)
    }

    fun updateBio(newBio: String) {
        _uiState.value = _uiState.value.copy(bio = newBio)
    }

    fun toggleDarkMode(isDark: Boolean) {
        _uiState.value = _uiState.value.copy(isDarkMode = isDark)
    }
}

