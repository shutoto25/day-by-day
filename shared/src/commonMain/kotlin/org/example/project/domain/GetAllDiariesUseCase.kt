package org.example.project.domain

import org.example.project.data.DiaryData

/**
 * 全日記データを取得するユースケース
 * @property diaryRepository 日記リポジトリ
 */
class GetAllDiariesUseCase(
    private val diaryRepository: DiaryRepository
) {
    /**
     * 全日記データを取得する
     * @return 日記データリスト
     */
    operator fun invoke(): List<DiaryData> = diaryRepository.getAllDiaries()
} 