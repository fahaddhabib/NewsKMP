package com.example.newskmp

import android.app.Application
import com.example.newskmp.di.sharedModules
import com.example.newskmp.di.sharedUIModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewsApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NewsApplication)
            modules(sharedModules + sharedUIModules)
        }
    }
}