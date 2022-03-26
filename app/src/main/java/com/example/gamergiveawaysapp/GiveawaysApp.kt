package com.example.gamergiveawaysapp

import android.app.Application
import com.example.gamergiveawaysapp.di.applicationModule
import com.example.gamergiveawaysapp.di.networkModule
import com.example.gamergiveawaysapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GiveawaysApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GiveawaysApp)
            modules(listOf(networkModule, applicationModule, viewModelModule))
        }
    }
}