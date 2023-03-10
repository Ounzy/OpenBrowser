package com.Ounzy.OpenBrowser.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TabDbItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun TabDao(): TabDao
}