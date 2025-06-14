package org.example.project.di

import org.koin.core.component.KoinComponent

/**
 * iOSからアクセスするためのヘルパークラス
 */
class IosAppDependencies : KoinComponent {
    // 必要なコンポーネントをプロパティとして公開
//    val nasaStore: NasaStore = get()
//    val platformStorage: PlatformStorage = get()

    companion object {
        fun create(): IosAppDependencies {
            initPlatformKoin()
            return IosAppDependencies()
        }
    }
}