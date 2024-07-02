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
    @ColumnInfo(name = "writer") // 리뷰 작성자명
    val writer: String,
    @ColumnInfo(name = "profile_uri") // 리뷰 작성자 프로필 사진
    val profileUri: String,
    @ColumnInfo(name = "is_admin_user") // 본인이 쓴 리뷰인지
    val isAdminUser: Boolean
) {
    fun getCopyWithNewId(newId: Long): Review {
        return Review(
            id = newId,
            name = this.name,
            reviewText = this.reviewText,
            imageUri = this.imageUri,
            writer = this.writer,
            profileUri = this.profileUri,
            isAdminUser = this.isAdminUser
        )
    }
}
