package com.example.register.app

import android.app.Application
import com.example.newtrainerapp.retrofit.ApiClient

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        ApiClient.instance(applicationContext)
    }
}