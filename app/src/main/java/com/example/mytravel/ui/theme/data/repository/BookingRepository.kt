package com.example.mytravel.ui.theme.data.repository

import com.example.mytravel.ui.theme.data.model.AccommodationBooking
import com.example.mytravel.ui.theme.data.model.ActivityBooking
import com.example.mytravel.ui.theme.data.model.FlightBooking
import com.example.mytravel.ui.theme.data.remote.TravelApiService
import com.example.mytravel.ui.theme.utils.Resource
import com.example.mytravel.ui.theme.utils.safeApiCall
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookingRepository @Inject constructor(
    private val apiService: TravelApiService,
    private val gson: Gson
) {
    fun getTripBookings(tripId: String): Flow<Resource<BookingsData>> = flow {
        emit(Resource.Loading)
        
        when (val response = safeApiCall { apiService.getTripBookings(tripId) }) {
            is Resource.Success -> {
                val data = response.data
                val flights = parseBookings<FlightBooking>(data["flights"])
                val accommodations = parseBookings<AccommodationBooking>(data["accommodations"])
                val activities = parseBookings<ActivityBooking>(data["activities"])
                
                emit(Resource.Success(BookingsData(flights, accommodations, activities)))
            }
            is Resource.Error -> emit(Resource.Error(response.message, response.code))
            Resource.Loading -> { /* Already emitted Loading state */ }
        }
    }
    
    private inline fun <reified T> parseBookings(data: Any?): List<T> {
        if (data == null) return emptyList()
        
        return try {
            val json = gson.toJson(data)
            val type = object : TypeToken<List<T>>() {}.type
            gson.fromJson(json, type)
        } catch (e: Exception) {
            emptyList()
        }
    }
}

data class BookingsData(
    val flights: List<FlightBooking>,
    val accommodations: List<AccommodationBooking>,
    val activities: List<ActivityBooking>
)
