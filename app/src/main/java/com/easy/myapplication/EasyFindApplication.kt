package com.easy.myapplication

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class EasyFindApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase
        startKoin {
            androidContext(this@EasyFindApplication)
            modules(storageModule, appModule)
        }
    }
}