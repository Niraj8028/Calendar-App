package com.example.calender

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class Calender: Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this) // Initialize ThreeTenABP
    }
}