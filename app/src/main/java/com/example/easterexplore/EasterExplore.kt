package com.example.easterexplore

import android.app.Application
import com.example.easterexplore.di.AppModuleImpl

class EasterExplore : Application() {

    companion object {
        lateinit var appModule: AppModuleImpl
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }

}