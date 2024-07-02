package com.madcamp.tabapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.madcamp.tabapp.data.database.DbConfig

@Entity(tableName = DbConfig.USER_TABLE)
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "login_id") val loginId: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "nickname") val nickname: String,
    @ColumnInfo(name = "profile_uri") val profileUri: String
)
