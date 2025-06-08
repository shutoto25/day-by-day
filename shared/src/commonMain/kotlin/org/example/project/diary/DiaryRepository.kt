package org.example.project.diary

import org.example.project.diary.data.DiaryData

/**
 * 日記データ取得用リポジトリインターフェース
 * @author shutoto25
 */
interface DiaryRepository {
    /**
     * 登録済み日記データ一覧を取得する
     * @return 日記データリスト
     */
    fun getAllDiaries(): List<DiaryData>
} 