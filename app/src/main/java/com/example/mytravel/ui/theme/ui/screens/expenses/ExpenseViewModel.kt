package com.example.mytravel.ui.theme.ui.screens.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytravel.ui.theme.data.model.Expense
import com.example.mytravel.ui.theme.data.repository.ExpenseRepository
import com.example.mytravel.ui.theme.utils.Resource
import com.example.mytravel.ui.theme.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {
    
    private val _expensesState = MutableStateFlow<UiState<List<Expense>>>(UiState.Loading)
    val expensesState: StateFlow<UiState<List<Expense>>> = _expensesState.asStateFlow()
    
    private val _selectedExpense = MutableStateFlow<Expense?>(null)
    val selectedExpense: StateFlow<Expense?> = _selectedExpense.asStateFlow()
    
    private val _showExpenseDetails = MutableStateFlow(false)
    val showExpenseDetails: StateFlow<Boolean> = _showExpenseDetails.asStateFlow()
    
    fun fetchExpenses(tripId: String) {
        viewModelScope.launch {
            expenseRepository.getTripExpenses(tripId).collect { result ->
                when (result) {
                    is Resource.Success -> _expensesState.update { UiState.Success(result.data) }
                    is Resource.Error -> _expensesState.update { UiState.Error(result.message) }
                    is Resource.Loading -> _expensesState.update { UiState.Loading }
                }
            }
        }
    }
    
    fun showExpenseDetails(expense: Expense) {
        _selectedExpense.update { expense }
        _showExpenseDetails.update { true }
    }
    
    fun hideExpenseDetails() {
        _showExpenseDetails.update { false }
    }
}
