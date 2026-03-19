package com.example.myapplication.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditProfileScreen(
    name: String,
    bio: String,
    onNameChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onSave: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {

        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Name") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = bio,
            onValueChange = onBioChange,
            label = { Text("Bio") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onSave) {
            Text("Save")
        }
    }
}
