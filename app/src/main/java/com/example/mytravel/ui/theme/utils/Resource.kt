package com.example.mytravel.ui.theme.utils

import retrofit2.Response

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String, val code: Int? = null) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}

// Generic function to handle API responses
suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                Resource.Success(body)
            } else {
                Resource.Error("Response body is null")
            }
        } else {
            Resource.Error("API call failed with code ${response.code()}", response.code())
        }
    } catch (e: Exception) {
        Resource.Error(e.message ?: "Unknown error occurred")
    }
}

// UI state for screens
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
