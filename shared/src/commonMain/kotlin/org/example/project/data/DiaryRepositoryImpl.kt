package org.example.project.data

import org.example.project.data.DiaryData

/**
 * 日記データ取得用リポジトリ実装（ダミー）
 * @author shutoto25
 */
class DiaryRepositoryImpl : DiaryRepository {
    override fun getAllDiaries(): List<DiaryData> = listOf(
        DiaryData("2024-06-01", "日記の内容1", "https://example.com/photo1.jpg"),
        DiaryData("2024-06-02", "日記の内容2", null),
        DiaryData("2024-06-03", "日記の内容3", "https://example.com/photo3.jpg")
    )
} 