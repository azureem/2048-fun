package com.example.fun2024.data

import android.app.Application
import com.example.fun2024.screens.MySharedPreferences

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        MySharedPreferences.init(this)
    }
}