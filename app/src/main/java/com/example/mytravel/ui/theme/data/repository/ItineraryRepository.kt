package com.example.mytravel.ui.theme.data.repository

import com.example.mytravel.ui.theme.data.model.ItineraryItem
import com.example.mytravel.ui.theme.data.remote.TravelApiService
import com.example.mytravel.ui.theme.utils.Resource
import com.example.mytravel.ui.theme.utils.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ItineraryRepository @Inject constructor(
    private val apiService: TravelApiService
) {
    fun getTripItinerary(tripId: String): Flow<Resource<List<ItineraryItem>>> = flow {
        emit(Resource.Loading)
        val response = safeApiCall { apiService.getTripItinerary(tripId) }
        emit(response)
    }
}
