package com.github.rahul_gill.doodhcard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.github.rahul_gill.doodhcard.data.MilkCardDatabase
import com.github.rahul_gill.doodhcard.data.MilkCardEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MilkCardViewModel(app: Application): AndroidViewModel(app) {
    private val dao = MilkCardDatabase.getDatabase(app).milkCardDao
    val data = dao.getAll()
    val total = dao.getTotal()

    fun insertEntry(vararg data: MilkCardEntity) = viewModelScope.launch(Dispatchers.IO) {
        dao.insert(*data)
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