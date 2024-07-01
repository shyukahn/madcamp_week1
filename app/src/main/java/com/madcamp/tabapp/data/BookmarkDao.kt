package com.madcamp.tabapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookmarkDao {
    @Insert
    suspend fun insert(bookmark: Bookmark)

    @Delete
    suspend fun delete(bookmark: Bookmark)

    @Query("SELECT * FROM bookmark_table WHERE user_id = :userId")
    suspend fun getAllBookmarksByUSerId(userId: Int): List<Bookmark>
}