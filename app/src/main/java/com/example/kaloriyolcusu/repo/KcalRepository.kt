package com.example.healtapp.repo

import androidx.lifecycle.LiveData
import com.example.healtapp.db.KcalDao
import com.example.healtapp.db.KcalDatabase
import com.example.healtapp.model.Kcal



class KcalRepository(private val db: KcalDatabase) {

    fun getAllKcalItemsLiveData(): LiveData<List<Kcal>> {
        return db.kcalDao().getAllKcalItemsLiveData()
    }

    suspend fun insertKcal(note: Kcal) {
        db.kcalDao().insertKcal(note)
    }

    suspend fun deleteAllKcalItems() {
        db.kcalDao().deleteAllKcalItems()
    }
}