package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun InfoItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            fontWeight = FontWeight.Bold
        )
        Text(text = value)
    }
}

