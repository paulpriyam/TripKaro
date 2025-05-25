package com.example.mytravel.ui.theme.data.repository

import com.example.mytravel.ui.theme.data.model.TripMember
import com.example.mytravel.ui.theme.data.remote.TravelApiService
import com.example.mytravel.ui.theme.utils.Resource
import com.example.mytravel.ui.theme.utils.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MemberRepository @Inject constructor(
    private val apiService: TravelApiService
) {
    fun getTripMembers(tripId: String): Flow<Resource<List<TripMember>>> = flow {
        emit(Resource.Loading)
        val response = safeApiCall { apiService.getTripMembers(tripId) }
        emit(response)
    }
    
    fun addTripMember(tripId: String, email: String): Flow<Resource<TripMember>> = flow {
        emit(Resource.Loading)
        val response = safeApiCall { 
            apiService.addTripMember(tripId, mapOf("email" to email)) 
        }
        emit(response)
    }
}
