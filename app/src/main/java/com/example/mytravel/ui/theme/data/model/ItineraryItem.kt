package com.example.mytravel.ui.theme.data.model

import com.google.gson.annotations.SerializedName

data class ItineraryItem(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("date") val date: String,
    @SerializedName("startTime") val startTime: String,
    @SerializedName("endTime") val endTime: String,
    @SerializedName("type") val type: String, // Can be "MORNING", "AFTERNOON", "EVENING"
    @SerializedName("location") val location: String?,
    @SerializedName("day") val day: Int
)
