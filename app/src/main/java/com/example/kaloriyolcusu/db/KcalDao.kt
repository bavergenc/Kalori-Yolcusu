package com.example.healtapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.healtapp.model.Kcal


@Dao
interface KcalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKcal(note: Kcal)

    @Query("SELECT * FROM kcal_items")
    fun getAllKcalItemsLiveData(): LiveData<List<Kcal>> // LiveData döndür

    @Query("DELETE FROM kcal_items")
    suspend fun deleteAllKcalItems()

    @Query("DELETE FROM kcal_items WHERE id = :id")
    suspend fun deleteKcalItemById(id: Long)
    @Delete
    suspend fun deleteKcal(kcal: Kcal)
}