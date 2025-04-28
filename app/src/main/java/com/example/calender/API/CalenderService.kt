package com.example.calender.API

import DeleteTaskRequest
import TaskListRequest
import TaskListResponse
import TaskRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CalenderService {
        @POST("/api/storeCalendarTask")
        suspend fun storeTask(@Body request: TaskRequest): Response<Unit>

        @GET("/api/getCalendarTaskLists")
        suspend fun getTasks(@Body request: TaskListRequest): Response<TaskListResponse>

        @POST("/api/deleteCalendarTask")
        suspend fun deleteTask(@Body request: DeleteTaskRequest): Response<Unit>
}