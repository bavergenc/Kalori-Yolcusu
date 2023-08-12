package com.example.healtapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.healtapp.model.Kcal

@Database(entities = [Kcal::class], version = 1, exportSchema = false)
abstract class KcalDatabase : RoomDatabase() {

    abstract fun kcalDao(): KcalDao

    companion object {
        @Volatile
        private var INSTANCE: KcalDatabase? = null

        fun getDatabase(context: Context): KcalDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KcalDatabase::class.java,
                    "kcal_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}