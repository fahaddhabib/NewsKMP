package com.example.newskmp

import android.app.Application
import com.example.newskmp.data.database.DatabaseDriverFactory
import com.example.newskmp.di.sharedModules
import com.example.newskmp.di.sharedUIModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class NewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NewsApplication)
            modules(
                sharedModules +
                        sharedUIModules +
                        module {
                            single { DatabaseDriverFactory(androidContext()) }
                        }
            )
        }
    }
}