package com.example.healtapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.healtapp.db.KcalDatabase
import com.example.healtapp.repo.KcalRepository

class KcalViewModelProviderFactory(
    val app: Application,
    private val noteRepository:KcalRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return KcalViewModel(app, noteRepository) as T
    }
}