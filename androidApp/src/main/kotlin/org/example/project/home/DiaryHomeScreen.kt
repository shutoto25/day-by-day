package org.example.project.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.example.project.presentation.DiaryHomeViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * ホーム画面（日記グリッド一覧）
 * @author shutoto25
 */
@Composable
fun DiaryHomeScreen(
    viewModel: DiaryHomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    Surface(color = MaterialTheme.colors.background) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "日記一覧",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(16.dp)
            )
            DiaryGridSection(diaryList = state.fullGridList)
        }
    }
}
