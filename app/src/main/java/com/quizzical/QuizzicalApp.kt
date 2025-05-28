package com.quizzical

import android.app.Application
import com.quizzical.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class QuizzicalApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@QuizzicalApp)
            modules(appModule)
        }
    }
}