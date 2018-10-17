package com.mathrace

import android.app.Application

class MathRaceApplication : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: MathRaceApplication? = null

        fun applicationContext() : MathRaceApplication {
            return instance as MathRaceApplication
        }
    }

    override fun onCreate() {
        super.onCreate()

    }
}