package org.example.project

import android.app.Application
import org.example.project.core.di.initKoinAndroid

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoinAndroid(this)
    }
}