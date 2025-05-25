package com.example.mytravel.ui.theme.data.model

import com.google.gson.annotations.SerializedName

data class TripMember(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("role") val role: String
)
