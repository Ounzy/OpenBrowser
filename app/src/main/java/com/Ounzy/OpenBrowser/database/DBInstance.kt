package com.Ounzy.OpenBrowser.database

import android.content.Context
import androidx.room.Room

object DBInstance {
    lateinit var Db: AppDatabase

    fun init(applicationContext: Context) {
        Db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "open-browser",
        ).allowMainThreadQueries().build()
    }
}
