package com.madcamp.tabapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DbConfig.USER_TABLE)
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "login_id") val loginId: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "full_name") val fullName: String,
    @ColumnInfo(name = "contact") val contact: String
)
