package com.example.matsubajun.githubsample

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.matsubajun.githubsample.di.module.apiModule
import com.example.matsubajun.githubsample.di.module.repositoryModule
import com.example.matsubajun.githubsample.di.module.viewModelModule
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(apiModule) + listOf(repositoryModule) + listOf(viewModelModule))
        }

        Fresco.initialize(this)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}