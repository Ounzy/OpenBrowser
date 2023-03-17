package com.Ounzy.OpenBrowser

import android.app.Application
import com.Ounzy.OpenBrowser.database.DBInstance

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        DBInstance.init(applicationContext)
        Preferences.init(applicationContext)
    }
}