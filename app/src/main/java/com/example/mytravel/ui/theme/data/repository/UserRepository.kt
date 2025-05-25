package com.example.mytravel.ui.theme.data.repository

import com.example.mytravel.ui.theme.data.model.User
import com.example.mytravel.ui.theme.data.remote.TravelApiService
import com.example.mytravel.ui.theme.utils.Resource
import com.example.mytravel.ui.theme.utils.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: TravelApiService
) {
    fun getUserProfile(userId: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading)
        val response = safeApiCall { apiService.getUser(userId) }
        emit(response)
    }
}
