package com.example.mytravel.ui.theme.data.model

import com.google.gson.annotations.SerializedName

sealed class Booking {
    abstract val id: String
    abstract val title: String
}

data class FlightBooking(
    @SerializedName("id") override val id: String,
    @SerializedName("title") override val title: String,
    @SerializedName("from") val from: String,
    @SerializedName("to") val to: String,
    @SerializedName("departureTime") val departureTime: String,
    @SerializedName("arrivalTime") val arrivalTime: String,
    @SerializedName("airline") val airline: String,
    @SerializedName("flightNumber") val flightNumber: String
) : Booking()

data class AccommodationBooking(
    @SerializedName("id") override val id: String,
    @SerializedName("title") override val title: String,
    @SerializedName("name") val name: String,
    @SerializedName("checkIn") val checkIn: String,
    @SerializedName("checkOut") val checkOut: String,
    @SerializedName("location") val location: String,
    @SerializedName("image") val image: String?
) : Booking()

data class ActivityBooking(
    @SerializedName("id") override val id: String,
    @SerializedName("title") override val title: String,
    @SerializedName("date") val date: String,
    @SerializedName("startTime") val startTime: String,
    @SerializedName("endTime") val endTime: String,
    @SerializedName("location") val location: String,
    @SerializedName("image") val image: String?
) : Booking()
