package com.madcamp.tabapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM user_table WHERE login_id = :userId")
    suspend fun getUserByLoginId(userId: String): User?

    @Query("SELECT * FROM ${DbConfig.USER_TABLE}")
    suspend fun getAllUsers(): List<User>
}
