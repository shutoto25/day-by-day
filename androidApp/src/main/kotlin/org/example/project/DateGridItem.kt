package org.example.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import org.example.project.diary.data.DiaryData

/**
 * グリッド1セル分の日付アイテム
 * @param diary DiaryData（日付・写真情報）
 */
@Composable
fun DateGridItem(diary: DiaryData, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (diary.photoUrl != null) {
                    Image(
                        painter = rememberAsyncImagePainter(diary.photoUrl),
                        contentDescription = "日記写真",
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Text(
                    text = diary.date,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
} 