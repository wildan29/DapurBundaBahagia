package com.example.dapurbundabahagia.domain.usecase

import com.example.dapurbundabahagia.data.models.RegisterModel
import com.google.gson.JsonObject
import retrofit2.Response

interface ApiHelper {
    suspend fun register(register: RegisterModel): Response<JsonObject>
}