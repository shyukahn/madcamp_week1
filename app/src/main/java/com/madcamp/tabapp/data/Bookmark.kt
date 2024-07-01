package com.madcamp.tabapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.madcamp.tabapp.data.database.DbConfig

@Entity(tableName = DbConfig.BOOKMARK_TABLE,)
data class Bookmark(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "bakery_id") val bakeryId: Int,
    @ColumnInfo(name = "is_bookmarked") var isBookmarked: Boolean
)