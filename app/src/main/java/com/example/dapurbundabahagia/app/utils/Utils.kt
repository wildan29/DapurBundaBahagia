package com.example.dapurbundabahagia.app.utils

import com.example.dapurbundabahagia.BuildConfig

object Utils {
    const val BASE_URL = "http://localhost:3000/"
    const val IS_DEBUG = BuildConfig.BUILD_TYPE == "debug"
    const val APPLICATION_ID = BuildConfig.APPLICATION_ID
}