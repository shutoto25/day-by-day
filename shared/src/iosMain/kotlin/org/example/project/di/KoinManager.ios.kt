package org.example.project.di

import org.example.project.core.di.initKoin

/**
 * iOS版の初期化はKoinManagerから直接行う
 */
actual fun initPlatformKoin() {
    initKoin()
}