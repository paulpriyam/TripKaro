package com.example.mytravel.ui.theme.data.repository

import com.example.mytravel.ui.theme.data.model.Expense
import com.example.mytravel.ui.theme.data.remote.TravelApiService
import com.example.mytravel.ui.theme.utils.Resource
import com.example.mytravel.ui.theme.utils.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExpenseRepository @Inject constructor(
    private val apiService: TravelApiService
) {
    fun getTripExpenses(tripId: String): Flow<Resource<List<Expense>>> = flow {
        emit(Resource.Loading)
        val response = safeApiCall { apiService.getTripExpenses(tripId) }
        emit(response)
    }
    
    fun addExpense(tripId: String, expenseData: Map<String, Any>): Flow<Resource<Expense>> = flow {
        emit(Resource.Loading)
        val response = safeApiCall { apiService.addExpense(tripId, expenseData) }
        emit(response)
    }
}
