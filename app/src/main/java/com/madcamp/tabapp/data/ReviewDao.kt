package com.madcamp.tabapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ReviewDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(review: Review)

    @Update
    suspend fun update(review: Review)

    @Delete
    suspend fun delete(review: Review)

    @Query("SELECT * from review_table WHERE id = :id")
    suspend fun getReviewById(id: Int): Review

    @Query("SELECT * from review_table")
    suspend fun getAllReviews(): List<Review>

    @Query("DELETE FROM review_table")
    suspend fun deleteAll()
}