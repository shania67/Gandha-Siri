package com.example.gandha_siri.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.example.gandha_siri.data.dao.TreeDao
import com.example.gandha_siri.data.model.Tree
import com.example.gandha_siri.data.model.Alert

@Database(
    entities = [Tree::class, Alert::class], // ✅ IMPORTANT FIX
    version = 2, // ⚠️ MUST INCREASE VERSION
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun treeDao(): TreeDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gandha_siri_db"
                )
                    .fallbackToDestructiveMigration() // ⚠️ avoids crash during version change
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}