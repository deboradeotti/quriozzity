package com.quriozzity

import android.app.Application
import com.quriozzity.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class QuriozzityApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@QuriozzityApp)
            modules(appModule)
        }
    }
}