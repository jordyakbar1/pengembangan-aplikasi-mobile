package com.example.myapplication.ui.viewmodel

data class ProfileUiState(
    val name: String = "",
    val bio: String = "",
    val email: String = "jordy@gmail.com",
    val phone: String = "123140",
    val location: String = "ngawi",
    val isDarkMode: Boolean = false
)
