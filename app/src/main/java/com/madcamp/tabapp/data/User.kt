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
    @ColumnInfo(name = "full_name") val nickname: String, // migration 하지 않기 위해 컬럼 이름은 수정 안함
    @ColumnInfo(name = "contact") val contact: String
)
