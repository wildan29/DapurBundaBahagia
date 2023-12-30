package com.example.dapurbundabahagia.data.models

import com.google.gson.annotations.SerializedName

data class RegisterModel(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
