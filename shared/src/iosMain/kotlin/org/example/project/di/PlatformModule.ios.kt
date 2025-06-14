package org.example.project.di

import io.ktor.client.*
import io.ktor.client.engine.darwin.*
import org.example.project.core.storage.PlatformStorage
import org.example.project.storage.createPlatformStorage
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * iOS向けのプラットフォームモジュール
 */
actual fun platformModule(): Module = module {
    // iOS向けのHttpClient実装
    single { HttpClient(Darwin) }
    single<PlatformStorage> { createPlatformStorage() }
}