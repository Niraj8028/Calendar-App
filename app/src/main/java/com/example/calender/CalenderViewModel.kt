package com.example.calender

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.threeten.bp.LocalDate

class CalenderViewModel: ViewModel() {
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> get() =_selectedDate

    private val _taskTitle =  MutableStateFlow("")
    val taskTitle: StateFlow<String> get() = _taskTitle

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog

    fun setDate(date: LocalDate) {
        _selectedDate.value = date
    }

    fun updateTitle(title: String) {
        _taskTitle.value = title
    }

    fun toggleDialog(show: Boolean) {
        _showDialog.value = show
    }

    fun addTask() {
        val task = Task(title = _taskTitle.value, date = _selectedDate.value)
        _tasks.value = _tasks.value + task
        Log.d("Add", _tasks.value.toString())

        _taskTitle.value = ""
        _showDialog.value = false
    }

    fun deleteTask(task: Task){
        _tasks.value = _tasks.value - task
    }
}