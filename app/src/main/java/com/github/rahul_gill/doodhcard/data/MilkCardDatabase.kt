package com.github.rahul_gill.doodhcard.data

import android.content.Context
import androidx.room.*

const val DATABASE_NAME = "milk_card_database"

@Database(
    entities = [MilkCardEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters(DateConverter::class)
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