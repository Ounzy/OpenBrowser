package com.Ounzy.OpenBrowser.database.OpenedTab

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OpenedTabInt(
    @PrimaryKey() val id: Int = 0,
    @ColumnInfo(name = "openedTab") val openedTabInt: Int,
)
