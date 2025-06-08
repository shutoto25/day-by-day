package org.example.project.diary

import org.example.project.diary.data.DiaryData

/**
 * 日記データ取得用リポジトリ
 * @author shutoto25
 */
class DiaryRepository {
    /**
     * 登録済み日記データ一覧を取得する（ダミー実装）
     * @return 日記データリスト
     */
    fun getAllDiaries(): List<DiaryData> = listOf(
        DiaryData("2024-06-01", "日記の内容1", "https://example.com/photo1.jpg"),
        DiaryData("2024-06-02", "日記の内容2", null),
        DiaryData("2024-06-03", "日記の内容3", "https://example.com/photo3.jpg")
    )
} 