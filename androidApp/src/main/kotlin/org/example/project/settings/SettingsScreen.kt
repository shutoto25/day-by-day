package org.example.project.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.example.project.presentation.SettingsViewModel
import org.koin.compose.koinInject
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinInject()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("aaa")
    }
}