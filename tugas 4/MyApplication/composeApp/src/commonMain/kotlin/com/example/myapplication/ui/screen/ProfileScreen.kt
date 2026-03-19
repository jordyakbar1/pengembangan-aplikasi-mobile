package com.example.myapplication.ui.screen

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.myapplication.model.Profile
import com.example.myapplication.viewmodel.ProfileViewModel
import com.example.myapplication.ui.components.ProfileCard
import com.example.myapplication.ui.components.DarkModeSwitch

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        ProfileCard(
            profile = Profile(
                name = uiState.name,
                bio = uiState.bio,
                email = uiState.email,
                phone = uiState.phone,
                location = uiState.location
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        //Edit Profile
        EditProfileScreen(
            name = uiState.name,
            bio = uiState.bio,
            onNameChange = { viewModel.updateName(it) },
            onBioChange = { viewModel.updateBio(it) },
            onSave = {

            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        //Dark Mode Toggle
        DarkModeSwitch(
            isDarkMode = uiState.isDarkMode,
            onToggle = { viewModel.toggleDarkMode(it) }
        )
    }
}
