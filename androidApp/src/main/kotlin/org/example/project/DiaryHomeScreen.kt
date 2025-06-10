package org.example.project

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.diary.DiaryRepositoryImpl
import org.example.project.diary.data.DiaryData
import org.example.project.core.util.DateUtils

/**
 * ホーム画面（日記グリッド一覧）
 * @author shutoto25
 */
@Composable
fun DiaryHomeScreen() {
    val diaryList: List<DiaryData> = DiaryRepositoryImpl().getAllDiaries()
    val allDates = DateUtils.getDatesOfCurrentMonth()
    val diaryMap = diaryList.associateBy { it.date }
    val fullGridList = allDates.map { date ->
        diaryMap[date] ?: DiaryData(date = date, content = "", photoUrl = null)
    }
    Surface(color = MaterialTheme.colors.background) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "日記一覧",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(16.dp)
            )
            DiaryGridSection(diaryList = fullGridList)
        }
    }
}
