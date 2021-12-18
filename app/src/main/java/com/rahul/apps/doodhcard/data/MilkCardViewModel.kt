package com.rahul.apps.doodhcard.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MilkCardViewModel(app: Application): AndroidViewModel(app) {
    private val dao = MilkCardDatabase.getDatabase(app).milkCardDao
    val data = dao.getAll()

    fun insertEntry(data: MilkCardEntity) = viewModelScope.launch(Dispatchers.IO) {
        dao.insert(data)
    }

    fun updateEntry(data: MilkCardEntity) = viewModelScope.launch(Dispatchers.IO) {
        dao.update(data)
    }

    fun deleteEntry(data: MilkCardEntity)=viewModelScope.launch(Dispatchers.IO) {
        dao.delete(data)
    }

    fun deleteAllEntries()=viewModelScope.launch {
        dao.clearAll()
    }
}