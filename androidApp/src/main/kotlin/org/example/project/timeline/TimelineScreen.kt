package org.example.project.timeline

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.example.project.presentation.TimelineViewModel
import org.koin.compose.koinInject

@Composable
fun TimelineScreen(
    viewModel: TimelineViewModel = koinInject()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("aaa")
    }
}