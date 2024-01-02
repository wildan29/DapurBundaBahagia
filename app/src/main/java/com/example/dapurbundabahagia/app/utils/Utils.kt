package com.example.dapurbundabahagia.app.utils

import android.view.View
import com.example.dapurbundabahagia.BuildConfig

object Utils {
    const val BASE_URL = "http://192.168.100.6:3000/"
    const val IS_DEBUG = BuildConfig.BUILD_TYPE == "debug"
    const val APPLICATION_ID = BuildConfig.APPLICATION_ID

    fun showLoading(view: View, isLoading: Boolean) =
        if (isLoading) view.visibility = View.VISIBLE
        else view.visibility = View.INVISIBLE

    const val GET_EMAIL_KEY = "GET_EMAIL_KEY"
    const val GET_PASSWORD_KEY = "GET_PASSWORD_KEY"
    const val GET_REQ_EMAIL = "GET_REQ_KEY"
    const val GET_REQ_PASSWORD = "GET_PASSWORD_KEY"
}