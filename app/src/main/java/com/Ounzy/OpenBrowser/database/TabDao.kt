package com.Ounzy.OpenBrowser.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TabDao {
    @Query("SELECT * FROM tabDbItem")
    fun getAll(): List<TabDbItem>

    @Query("SELECT * FROM tabDbItem WHERE uid = 1")
    fun selectbyId(): TabDbItem

    @Insert
    fun insert(tabDbItem: TabDbItem)

    @Delete
    fun delete(tabDbItem: TabDbItem)
}
