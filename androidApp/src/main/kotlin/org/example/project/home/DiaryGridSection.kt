package org.example.project.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.data.DiaryData

/**
 * 日記グリッド一覧Section
 * @param diaryList 日記データリスト
 * @param modifier Modifier
 */
@Composable
fun DiaryGridSection(
    diaryList: List<DiaryData>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(diaryList) { diary ->
            DateGridItem(diary = diary)
        }
    }
} 