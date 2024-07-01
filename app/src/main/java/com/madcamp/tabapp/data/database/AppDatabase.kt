package com.madcamp.tabapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.madcamp.tabapp.data.Bookmark
import com.madcamp.tabapp.data.BookmarkDao
import com.madcamp.tabapp.data.User
import com.madcamp.tabapp.data.UserDao

@Database(entities = [User::class, Bookmark::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun bookmarkDao(): BookmarkDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DbConfig.ROOM_DB_NAME
                )
                    .addMigrations(AppDatabase.MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
        // For database migration
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Define SQL statements for the migration
                // For example: database.execSQL("ALTER TABLE your_table ADD COLUMN new_column_name TEXT")
                // BOOKMARK_TABLE 생성
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS ${DbConfig.BOOKMARK_TABLE} (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        user_id INTEGER NOT NULL,
                        bakery_id INTEGER NOT NULL
                    )
                """.trimIndent())
            }
        }
    }
}