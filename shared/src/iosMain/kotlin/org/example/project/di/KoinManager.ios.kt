package org.example.project.di

/**
 * iOS版の初期化はKoinManagerから直接行う
 */
actual fun initPlatformKoin() {
    initKoin()
}