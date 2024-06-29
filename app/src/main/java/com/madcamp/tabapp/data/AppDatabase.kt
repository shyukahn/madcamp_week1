package com.madcamp.tabapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

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
                    //.addMigrations(AppDatabase.MIGRATION_1_2)  // Add your migration(s)
                    .build()
                INSTANCE = instance
                instance // TODO: 물어보기
            }
        }
    }

    /*
    // For database migration
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Define SQL statements for the migration
            // For example: database.execSQL("ALTER TABLE your_table ADD COLUMN new_column_name TEXT")
        }
    }
     */
}