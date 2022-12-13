package com.zero.pengusirburung.root

import android.app.Application
import com.zero.pengusirburung.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    viewModelModule
                )
            )
        }
    }
}