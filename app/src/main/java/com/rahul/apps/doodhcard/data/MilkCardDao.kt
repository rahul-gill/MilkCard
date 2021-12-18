package com.rahul.apps.doodhcard.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MilkCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg data: MilkCardEntity)

    @Update
    suspend fun update(data: MilkCardEntity)

    @Delete
    suspend fun delete(data: MilkCardEntity)

    @Query("DELETE FROM $MILK_CARD_TABLE")
    suspend fun clearAll()

    @Query("SELECT * FROM $MILK_CARD_TABLE")
    fun getAll(): LiveData<List<MilkCardEntity>>

    @Query("SELECT SUM(a.weight * a.rate) FROM $MILK_CARD_TABLE AS a")
    fun getTotal(): LiveData<Double>
}
