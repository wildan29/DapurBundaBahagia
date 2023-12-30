package com.example.dapurbundabahagia.data.repo

import com.example.dapurbundabahagia.data.api.ApiService
import com.example.dapurbundabahagia.data.models.RegisterModel
import com.example.dapurbundabahagia.domain.usecase.ApiHelper
import com.google.gson.JsonObject
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun login(login: RegisterModel): Response<JsonObject> {
        return apiService.login(login)
    }
}