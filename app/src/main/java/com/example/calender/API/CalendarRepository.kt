package com.example.calender.API

import DeleteTaskRequest
import TaskDetail
import TaskListRequest
import TaskModel
import TaskRequest
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CalendarRepository (
    private val apiService: CalenderService)  {

     fun getTasks(): Flow<List<TaskModel>> = flow {
        try {
            val response = apiService.getTasks(TaskListRequest(1001))
            Log.d("Response", response.toString());
            if (response.isSuccessful) {
                emit(response.body()?.tasks ?: emptyList())
            } else {
                throw Exception("Failed to fetch tasks: ${response.code()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun addTask(task: TaskDetail) {
        val response = apiService.storeTask(TaskRequest(1001, task))
        Log.d("Response", response.body().toString())
        if (!response.isSuccessful) {
            throw Exception("Failed to add task: ${response.code()}")
        }
    }

     suspend fun deleteTask(taskId: Int) {
        val response = apiService.deleteTask(DeleteTaskRequest(1001, taskId))
         Log.d("Response", response.toString());
        if (!response.isSuccessful) {
            throw Exception("Failed to delete task: ${response.code()}")
        }
    }

}