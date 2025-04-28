package com.example.calender

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp

//@HiltAndroidApp
class Calender: Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this) // Initialize ThreeTenABP
    }
}