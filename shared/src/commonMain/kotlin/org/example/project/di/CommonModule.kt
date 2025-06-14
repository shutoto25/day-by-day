package org.example.project.di

import kotlinx.serialization.json.Json
import org.koin.dsl.module
import org.example.project.data.DiaryRepository
import org.example.project.data.DiaryRepositoryImpl
import org.example.project.domain.usecase.GetAllDiariesUseCase

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