package com.example.myapplication.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

@Composable
fun DarkModeSwitch(
    isDarkMode: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Dark Mode")

        Spacer(modifier = Modifier.width(8.dp))

        Switch(
            checked = isDarkMode,
            onCheckedChange = onToggle
        )
    }
}
