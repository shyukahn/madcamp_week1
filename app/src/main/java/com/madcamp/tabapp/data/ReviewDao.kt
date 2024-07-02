package com.madcamp.tabapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.madcamp.tabapp.data.database.DbConfig

@Dao
interface ReviewDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(review: Review): Long

    @Update
    suspend fun update(review: Review)

    @Delete
    suspend fun delete(review: Review)

    @Query("SELECT * from ${DbConfig.REVIEW_TABLE} WHERE id = :id")
    suspend fun getReviewById(id: Int): Review

    @Query("SELECT * FROM ${DbConfig.REVIEW_TABLE} WHERE is_admin_user = 1")
    suspend fun getAdminReviews(): List<Review>

    @Query("SELECT * from ${DbConfig.REVIEW_TABLE}")
    suspend fun getAllReviews(): List<Review>

    @Query("DELETE FROM ${DbConfig.REVIEW_TABLE}")
    suspend fun deleteAll()
}