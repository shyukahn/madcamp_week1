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

@Database(entities = [User::class, Bookmark::class], version = 3, exportSchema = false)
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
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
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

        // For database migration from version 2 to 3
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Create new table with the desired schema
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS bookmark_table_new (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        bakery_id INTEGER NOT NULL,
                        is_bookmarked INTEGER NOT NULL DEFAULT 0
                    )
                """.trimIndent())

                // Copy the data from the old table to the new table
                db.execSQL("""
                    INSERT INTO bookmark_table_new (id, bakery_id, is_bookmarked)
                    SELECT id, bakery_id, 0 FROM ${DbConfig.BOOKMARK_TABLE}
                """.trimIndent())

                // Drop the old table
                db.execSQL("DROP TABLE ${DbConfig.BOOKMARK_TABLE}")

                // Rename the new table to the old table name
                db.execSQL("ALTER TABLE bookmark_table_new RENAME TO ${DbConfig.BOOKMARK_TABLE}")
            }
        }
    }
}