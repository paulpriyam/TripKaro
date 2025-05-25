package com.example.mytravel.ui.theme.data.model

import com.google.gson.annotations.SerializedName

data class Trip(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("destination") val destination: String,
    @SerializedName("startDate") val startDate: String,
    @SerializedName("endDate") val endDate: String,
    @SerializedName("budget") val budget: Double?,
    @SerializedName("remaining") val remaining: Double?,
    @SerializedName("memberCount") val memberCount: Int,
    @SerializedName("image") val image: String?
)
