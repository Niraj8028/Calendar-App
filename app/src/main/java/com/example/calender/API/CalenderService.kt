package com.example.calender.API

import DeleteTaskRequest
import TaskListRequest
import TaskRequest
import TaskResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CalenderService {
        @POST("/api/storeCalendarTask")
        suspend fun storeTask(@Body request: TaskRequest): Response<Unit>

        @POST("api/getCalendarTaskList")
        suspend fun getTasks(@Body request: TaskListRequest): Response<TaskResponse>

        @POST("/api/deleteCalendarTask")
        suspend fun deleteTask(@Body request: DeleteTaskRequest): Response<Unit>
}