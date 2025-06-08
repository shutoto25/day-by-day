package org.example.project.diary.usecase

import org.example.project.diary.DiaryRepository
import org.example.project.diary.data.DiaryData

/**
 * 全日記データを取得するユースケース
 * @property diaryRepository 日記リポジトリ
 */
class GetAllDiariesUseCase(private val diaryRepository: DiaryRepository) {
    /**
     * 全日記データを取得する
     * @return 日記データリスト
     */
    fun execute(): List<DiaryData> = diaryRepository.getAllDiaries()
} 