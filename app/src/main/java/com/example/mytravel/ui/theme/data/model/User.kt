package com.example.mytravel.ui.theme.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("age") val age: Int?,
    @SerializedName("email") val email: String,
    @SerializedName("shareCode") val shareCode: String?
)
