package org.example.project.diary.usecase

import org.example.project.diary.DiaryRepository
import org.example.project.diary.data.DiaryData
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * GetAllDiariesUseCaseのユニットテスト
 * TDD Boot Camp流のGiven-When-Then構文・1メソッド1アサート・日本語メソッド名で記載
 */
class GetAllDiariesUseCaseTest {
    /**
     * ダミーリポジトリ実装
     */
    private class DummyDiaryRepository : DiaryRepository {
        override fun getAllDiaries(): List<DiaryData> = listOf(
            DiaryData("2024-06-01", "テスト日記1", null),
            DiaryData("2024-06-02", "テスト日記2", "https://example.com/photo2.jpg")
        )
    }

    @Test
    fun 全件取得でリストサイズが2件である() {
        // Given: ダミーリポジトリとユースケース
        val useCase = GetAllDiariesUseCase(DummyDiaryRepository())
        // When: ユースケースを実行
        val diaries = useCase.execute()
        // Then: 結果のサイズが2である
        assertEquals(2, diaries.size)
    }

    @Test
    fun `最初の要素の日付が2024-06-01である`() {
        val useCase = GetAllDiariesUseCase(DummyDiaryRepository())
        val diaries = useCase.execute()
        assertEquals("2024-06-01", diaries[0].date)
    }

    @Test
    fun `2件目の本文がテスト日記2である`() {
        val useCase = GetAllDiariesUseCase(DummyDiaryRepository())
        val diaries = useCase.execute()
        assertEquals("テスト日記2", diaries[1].content)
    }
} 