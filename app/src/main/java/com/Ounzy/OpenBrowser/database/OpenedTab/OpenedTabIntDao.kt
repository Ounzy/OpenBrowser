package com.Ounzy.OpenBrowser.database.OpenedTab

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface OpenedTabIntDao {

    @Query("SELECT * FROM openedTabInt")
    fun getAll(): List<OpenedTabInt>

    @Insert
    fun add(openedTabInt: OpenedTabInt)

    @Update
    fun update(openedTabInt: OpenedTabInt)
}
