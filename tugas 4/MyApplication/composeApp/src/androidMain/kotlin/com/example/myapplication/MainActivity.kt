package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.*
import androidx.compose.material3.*
import com.example.myapplication.viewmodel.ProfileViewModel
import com.example.myapplication.ui.screen.ProfileScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: ProfileViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsState()

            MaterialTheme(
                colorScheme = if (uiState.isDarkMode) {
                    darkColorScheme()
                } else {
                    lightColorScheme()
                }
            ) {
                ProfileScreen(viewModel)
            }
        }
    }
}
