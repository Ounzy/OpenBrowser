package com.Ounzy.OpenBrowser.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.Ounzy.OpenBrowser.database.OpenedTab.OpenedTabInt
import com.Ounzy.OpenBrowser.database.OpenedTab.OpenedTabIntDao
import com.Ounzy.OpenBrowser.database.TabDBItem.TabDao
import com.Ounzy.OpenBrowser.database.TabDBItem.TabDbItem

@Database(entities = [TabDbItem::class, OpenedTabInt::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun TabDao(): TabDao
    abstract fun openedTabIntDao(): OpenedTabIntDao
}
