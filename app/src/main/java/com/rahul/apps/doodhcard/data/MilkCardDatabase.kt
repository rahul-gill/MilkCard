package com.rahul.apps.doodhcard.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

const val DATABASE_NAME = "milk_card_database"

@Database(
    entities = [MilkCardEntity::class],
    version = 1,
    exportSchema = false)
abstract class MilkCardDatabase : RoomDatabase() {

    abstract val milkCardDao: MilkCardDao

    companion object {
        @Volatile
        private var instance: MilkCardDatabase? = null

        fun getDatabase(context: Context): MilkCardDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MilkCardDatabase::class.java,
                    DATABASE_NAME
                ).build()
            }
        }
    }
}