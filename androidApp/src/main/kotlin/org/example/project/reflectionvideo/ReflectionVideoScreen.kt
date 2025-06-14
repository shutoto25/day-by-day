package org.example.project.reflectionvideo

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.example.project.presentation.ReflectionVideoViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ReflectionVideoScreen(
    viewModel: ReflectionVideoViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("aaa")
    }
}