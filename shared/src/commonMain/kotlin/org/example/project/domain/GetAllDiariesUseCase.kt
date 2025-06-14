package org.example.project.domain.usecase

import org.example.project.data.DiaryRepository
import org.example.project.data.DiaryData

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