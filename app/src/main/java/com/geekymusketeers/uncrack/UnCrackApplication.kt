package com.geekymusketeers.uncrack

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class UnCrackApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}