package org.example.project.di

import kotlinx.serialization.json.Json
import org.koin.dsl.module
import org.example.project.diary.DiaryRepository
import org.example.project.diary.DiaryRepositoryImpl
import org.example.project.diary.usecase.GetAllDiariesUseCase

// 両プラットフォー用の共通モジュール設定
val commonModule = module {
    // Json
    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            coerceInputValues = true
            encodeDefaults = true
        }
    }

    /**
     * 日記リポジトリのDI定義
     */
    single<DiaryRepository> { DiaryRepositoryImpl() }

    single { GetAllDiariesUseCase(get()) }
}