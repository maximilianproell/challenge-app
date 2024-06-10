package com.lequest.app

import android.app.Application
import data.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ui.di.viewModelModule

class LeQuestApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@LeQuestApplication)

            modules(dataModule, viewModelModule)
        }
    }
}