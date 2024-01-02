package com.example.dapurbundabahagia.data.repo

import com.example.dapurbundabahagia.data.models.RegisterModel
import com.example.dapurbundabahagia.domain.usecase.ApiHelper
import javax.inject.Inject

class MainRepo @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun register(register: RegisterModel) = apiHelper.register(register)
}