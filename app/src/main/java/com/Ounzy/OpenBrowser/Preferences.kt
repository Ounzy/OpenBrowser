package com.Ounzy.OpenBrowser

import android.content.Context
import android.content.SharedPreferences

object Preferences {
    private const val prefFileName = "app_prefs"
    lateinit var instance: SharedPreferences

    const val themeModePrefKey = "theme"
    const val mainStartUrl = "mainStartUrl"

    fun init(context: Context) {
        instance = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)
    }
}
