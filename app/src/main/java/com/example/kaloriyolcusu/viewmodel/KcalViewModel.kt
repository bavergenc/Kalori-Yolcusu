package com.example.healtapp.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.healtapp.db.KcalDatabase
import com.example.healtapp.model.Kcal
import com.example.healtapp.repo.KcalRepository
import kotlinx.coroutines.launch
class KcalViewModel(
    app: Application,
    private val noteRepository: KcalRepository
) : AndroidViewModel(app) {

    fun addKcalNoteAndShowToast(note: Kcal, context: Context) {
        viewModelScope.launch {
            try {
                noteRepository.insertKcal(note)
                Toast.makeText(context, "Kcal eklendi!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Kcal eklenirken bir hata oluştu!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getAllKcalItemsLiveData(): LiveData<List<Kcal>> {
        return noteRepository.getAllKcalItemsLiveData()
    }

    fun deleteAllKcalItems() {
        viewModelScope.launch {
            noteRepository.deleteAllKcalItems()
        }
    }
}

