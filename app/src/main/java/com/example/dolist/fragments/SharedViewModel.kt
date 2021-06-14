package com.example.dolist.fragments

import android.app.Application
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.dolist.R
import com.example.dolist.data.models.Priority
import com.example.dolist.data.models.ToDoData

class SharedViewModel(application: Application): AndroidViewModel(application) {


    val emptyDataBase: MutableLiveData<Boolean> = MutableLiveData(false)
    fun checkIfDatbaseEmpty(toDoData: List<ToDoData>) {
        emptyDataBase.value = toDoData.isEmpty()
    }


    val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            when(position) {

                0 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,
                    R.color.priorityHigh))}

                1 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,
                    R.color.priorityMedium))}

                2 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,
                    R.color.priorityLow))}

            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    fun verifyData(title: String, desc: String): Boolean {
        return title.isNotEmpty() && desc.isNotEmpty()
    }

    fun parsePriority(priority: String): Priority {
        return when(priority) {
            "High Priority" -> Priority.HIGH
            "Medium Priority" -> Priority.MEDIUM
            "Low Priority" -> Priority.LOW

            else -> Priority.LOW
        }
    }

    fun parsePriorityToInt(priority: Priority): Int {
        return priority.ordinal
    }




}