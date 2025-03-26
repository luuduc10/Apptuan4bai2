package com.example.app_api.api

import com.example.app_api.model.TaskResponse
import retrofit2.Response
import retrofit2.http.GET

interface TaskApi {
    @GET("researchUTH/tasks")
    suspend fun getTasks(): Response<TaskResponse> // Updated return type
}