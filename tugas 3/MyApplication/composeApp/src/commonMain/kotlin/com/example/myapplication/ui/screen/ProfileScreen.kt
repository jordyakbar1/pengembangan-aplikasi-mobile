package com.example.myapplication.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myapplication.model.Profile
import com.example.myapplication.ui.components.ProfileCard

@Composable
fun ProfileScreen() {

    val profile = Profile(
        name = "John Doe",
        bio = "Android Developer",
        email = "john@example.com",
        phone = "+62 812345678",
        location = "Jakarta"
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ProfileCard(profile)
    }
}
