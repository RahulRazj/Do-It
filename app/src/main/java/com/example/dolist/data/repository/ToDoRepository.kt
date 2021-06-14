package com.example.dolist.data.repository

import android.view.SearchEvent
import androidx.lifecycle.LiveData
import com.example.dolist.data.ToDoDao
import com.example.dolist.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {

    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()

    val sortByPriorityHigh: LiveData<List<ToDoData>> = toDoDao.sortByPriorityHigh()
    val sortByPriorityLow: LiveData<List<ToDoData>> = toDoDao.sortByPriorityLow()

    suspend fun insertData(toDoData: ToDoData) {
        toDoDao.insertData(toDoData)
    }

    suspend fun updateData(toDoData: ToDoData) {
        toDoDao.updateData(toDoData)
    }

    suspend fun deleteData(toDoData: ToDoData) {
        toDoDao.deleteData(toDoData)
    }

    suspend fun deleteAll() {
        toDoDao.deleteAll()
    }

    fun searchDataBase(searchQuery: String): LiveData<List<ToDoData>> {
        return toDoDao.searchDataBase(searchQuery)
    }

//    fun sortByPriorityHigh(): LiveData<List<ToDoData>> {
//        return toDoDao.sortByPriorityHigh()
//    }
//
//    fun sortByPriorityLow(): LiveData<List<ToDoData>> {
//        return toDoDao.sortByPriorityLow()
//    }
}