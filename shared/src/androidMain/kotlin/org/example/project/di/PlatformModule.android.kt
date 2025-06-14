package org.example.project.di

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import io.ktor.client.*
import org.example.project.storage.AndroidPlatformStorage
import org.example.project.presentation.AddEntryViewModel
import org.example.project.presentation.DiaryHomeViewModel
import org.example.project.presentation.ReflectionVideoViewModel
import org.example.project.presentation.SettingsViewModel
import org.example.project.presentation.TimelineViewModel
import org.example.project.storage.PlatformStorage
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Android固有のモジュール設定
 */
actual fun platformModule(): Module = module {
    listOf(dataModule() + presentationModule())
}

private fun dataModule(): Module = module {
    // PlatformStorage
    single<PlatformStorage> { AndroidPlatformStorage(get()) }
    // HttpClient
    single { HttpClient() }
    // SharedPreferences
    single<Settings> {
        val context: Context = get()
        SharedPreferencesSettings(
            context.getSharedPreferences("app_storage", Context.MODE_PRIVATE)
        )
    }
}

private fun presentationModule(): Module = module {
    viewModel { DiaryHomeViewModel(get()) }
    viewModel { AddEntryViewModel() }
    viewModel { ReflectionVideoViewModel() }
    viewModel { SettingsViewModel() }
    viewModel { TimelineViewModel() }
}