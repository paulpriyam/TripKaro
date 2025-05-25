package com.example.mytravel.ui.theme.data.repository

import com.example.mytravel.ui.theme.data.model.*
import com.example.mytravel.ui.theme.data.remote.TravelApiService
import com.example.mytravel.ui.theme.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TravelRepository @Inject constructor(
    private val apiService: TravelApiService
) {
    // Trips
    fun getTrips(): Flow<UiState<List<Trip>>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.getTrips()
            if (response.isSuccessful && response.body() != null) {
                emit(UiState.Success(response.body()!!))
            } else {
                emit(UiState.Error("Failed to load trips: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(UiState.Error("An error occurred: ${e.message}"))
        }
    }

    fun getTrip(tripId: String): Flow<UiState<Trip>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.getTrip(tripId)
            if (response.isSuccessful && response.body() != null) {
                emit(UiState.Success(response.body()!!))
            } else {
                emit(UiState.Error("Failed to load trip: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(UiState.Error("An error occurred: ${e.message}"))
        }
    }

    // Trip Members
    fun getTripMembers(tripId: String): Flow<UiState<List<TripMember>>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.getTripMembers(tripId)
            if (response.isSuccessful && response.body() != null) {
                emit(UiState.Success(response.body()!!))
            } else {
                emit(UiState.Error("Failed to load trip members: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(UiState.Error("An error occurred: ${e.message}"))
        }
    }

    fun addTripMember(tripId: String, email: String, name: String): Flow<UiState<TripMember>> = flow {
        emit(UiState.Loading)
        try {
            val memberData = mapOf(
                "email" to email,
                "name" to name
            )
            val response = apiService.addTripMember(tripId, memberData)
            if (response.isSuccessful && response.body() != null) {
                emit(UiState.Success(response.body()!!))
            } else {
                emit(UiState.Error("Failed to add member: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(UiState.Error("An error occurred: ${e.message}"))
        }
    }

    // Itinerary
    fun getTripItinerary(tripId: String): Flow<UiState<List<ItineraryItem>>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.getTripItinerary(tripId)
            if (response.isSuccessful && response.body() != null) {
                emit(UiState.Success(response.body()!!))
            } else {
                emit(UiState.Error("Failed to load itinerary: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(UiState.Error("An error occurred: ${e.message}"))
        }
    }

    // Expenses
    fun getTripExpenses(tripId: String): Flow<UiState<List<Expense>>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.getTripExpenses(tripId)
            if (response.isSuccessful && response.body() != null) {
                emit(UiState.Success(response.body()!!))
            } else {
                emit(UiState.Error("Failed to load expenses: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(UiState.Error("An error occurred: ${e.message}"))
        }
    }

    fun addExpense(
        tripId: String,
        title: String,
        amount: Double,
        currency: String,
        category: String,
        paidBy: String,
        splitWith: List<String>
    ): Flow<UiState<Expense>> = flow {
        emit(UiState.Loading)
        try {
            val expenseData = mapOf(
                "title" to title,
                "amount" to amount,
                "currency" to currency,
                "category" to category,
                "paidBy" to paidBy,
                "splitWith" to splitWith
            )
            val response = apiService.addExpense(tripId, expenseData)
            if (response.isSuccessful && response.body() != null) {
                emit(UiState.Success(response.body()!!))
            } else {
                emit(UiState.Error("Failed to add expense: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(UiState.Error("An error occurred: ${e.message}"))
        }
    }

    // Bookings
    fun getTripBookings(tripId: String): Flow<UiState<Map<String, List<Any>>>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.getTripBookings(tripId)
            if (response.isSuccessful && response.body() != null) {
                emit(UiState.Success(response.body()!!))
            } else {
                emit(UiState.Error("Failed to load bookings: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(UiState.Error("An error occurred: ${e.message}"))
        }
    }
}
