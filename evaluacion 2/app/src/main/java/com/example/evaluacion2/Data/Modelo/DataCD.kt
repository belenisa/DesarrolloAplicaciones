package com.example.evaluacion2.Modelo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [CD::class],
    version = 3,
    exportSchema = true
)
abstract class DataCD : RoomDatabase() {
    abstract fun CDDao(): CDsDao

    companion object {
        @Volatile
        private var INSTANCE: DataCD? = null

        fun getInstance(context: Context): DataCD =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    DataCD::class.java,
                    "app.db"
                )
                    .fallbackToDestructiveMigration() // 👈 Esto borra y recrea la DB si hay cambios
                    .build()
                    .also { INSTANCE = it }
            }
    }
}