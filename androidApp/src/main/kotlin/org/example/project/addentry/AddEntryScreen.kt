package org.example.project.addentry

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.example.project.presentation.AddEntryViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddEntryScreen(
    viewModel: AddEntryViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("aaa")
    }
}