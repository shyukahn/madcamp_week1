package com.madcamp.tabapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.madcamp.tabapp.data.database.DbConfig

@Entity(tableName = DbConfig.REVIEW_TABLE)
data class Review(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name") // 가게 이름
    val name: String,
    @ColumnInfo(name = "review_text") // 리뷰 내용
    val reviewText: String,
    @ColumnInfo(name = "image_uri") // 사진 Uri 주소
    val imageUri: String,
    @ColumnInfo(name = "writer") // 리뷰 작성자명
    val writer: String
)
