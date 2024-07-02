package com.madcamp.tabapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.madcamp.tabapp.data.database.DbConfig

@Entity(tableName = DbConfig.REVIEW_TABLE)
data class Review(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "name") // 가게 이름
    val name: String,
    @ColumnInfo(name = "review_text") // 리뷰 내용
    val reviewText: String,
    @ColumnInfo(name = "image_uri") // 사진 Uri 주소
    val imageUri: String,
    @ColumnInfo(name = "user_id")
    val userId: Int
) {
    fun getCopyWithNewId(newId: Long): Review {
        return Review(
            id = newId,
            name = this.name,
            reviewText = this.reviewText,
            imageUri = this.imageUri,
            userId = this.userId
        )
    }
}
