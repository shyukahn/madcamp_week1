package com.madcamp.tabapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.madcamp.tabapp.data.database.DbConfig

@Dao
interface BookmarkDao {
    @Insert
    suspend fun insert(bookmark: Bookmark)

    @Update
    suspend fun update(bookmark: Bookmark)

    @Delete
    suspend fun delete(bookmark: Bookmark)

    @Query("SELECT * FROM ${DbConfig.BOOKMARK_TABLE} WHERE bakery_id = :bakeryId")
    suspend fun getBookmark(bakeryId: Int): Bookmark?

    @Query("SELECT * FROM ${DbConfig.BOOKMARK_TABLE} WHERE is_bookmarked = 1")
    suspend fun getAllBookmarks(): List<Bookmark>
}