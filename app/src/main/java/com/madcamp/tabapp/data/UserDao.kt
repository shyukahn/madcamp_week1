package com.madcamp.tabapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query
import com.madcamp.tabapp.data.database.DbConfig

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM ${DbConfig.USER_TABLE} WHERE id = :id")
    suspend fun getUserById(id: Int): User

    @Query("SELECT * FROM ${DbConfig.USER_TABLE} WHERE login_id = :userId")
    suspend fun getUserByLoginId(userId: String): User?

    @Query("SELECT COUNT(*) FROM ${DbConfig.USER_TABLE}")
    fun getUserCount(): Int
}
