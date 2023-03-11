package com.Ounzy.OpenBrowser.database

import androidx.room.*

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

    @Update
    fun update(tabDbItem: TabDbItem)
}
