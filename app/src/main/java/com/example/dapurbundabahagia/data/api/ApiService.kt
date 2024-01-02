package com.example.dapurbundabahagia.data.api

import com.example.dapurbundabahagia.data.models.RegisterModel
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/users")
    suspend fun register(
        @Body register: RegisterModel
    ): Response<JsonObject>
}