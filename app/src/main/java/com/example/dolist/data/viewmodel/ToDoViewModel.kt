package com.example.dolist.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.dolist.data.ToDoDatabase
import com.example.dolist.data.models.ToDoData
import com.example.dolist.data.repository.ToDoRepository
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application): AndroidViewModel(application) {

    private val toDoDao = ToDoDatabase.getDatabase(application).toDoDao()

    private val repository: ToDoRepository = ToDoRepository(toDoDao)
    val getAllData: LiveData<List<ToDoData>> = repository.getAllData

    val sortByPriorityLow: LiveData<List<ToDoData>> = repository.sortByPriorityLow
    val sortByPriorityHigh: LiveData<List<ToDoData>> = repository.sortByPriorityHigh

    fun insertData(toDoData: ToDoData) {
        viewModelScope.launch {
            repository.insertData(toDoData)
        }
    }

    fun updateData(toDoData: ToDoData) {
        viewModelScope.launch {
            repository.updateData(toDoData)
        }
    }

    fun deleteData(toDoData: ToDoData) {
        viewModelScope.launch {
            repository.deleteData(toDoData)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    fun searchDataBase(searchQuery: String): LiveData<List<ToDoData>> {
        return repository.searchDataBase(searchQuery)
    }

//    fun sortByPriorityHigh(): LiveData<List<ToDoData>> {
//            return repository.sortByPriorityHigh()
//    }
//
//    fun sortByPriorityLow(): LiveData<List<ToDoData>> {
//        return repository.sortByPriorityLow()
//    }

}