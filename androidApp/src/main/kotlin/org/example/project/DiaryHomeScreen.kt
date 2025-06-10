package org.example.project

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.diary.DiaryHomeViewModel
import org.example.project.diary.usecase.GetAllDiariesUseCase
import org.example.project.diary.DiaryRepositoryImpl
import org.example.project.core.util.DateUtils
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

/**
 * ホーム画面（日記グリッド一覧）
 * @author shutoto25
 */
@Composable
fun DiaryHomeScreen() {
    val viewModel = DiaryHomeViewModel(GetAllDiariesUseCase(DiaryRepositoryImpl()))
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
