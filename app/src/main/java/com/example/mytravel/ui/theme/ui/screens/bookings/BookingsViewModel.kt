package com.example.mytravel.ui.theme.ui.screens.bookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytravel.ui.theme.data.repository.BookingRepository
import com.example.mytravel.ui.theme.data.repository.BookingsData
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
class BookingsViewModel @Inject constructor(
    private val bookingRepository: BookingRepository
) : ViewModel() {
    
    private val _bookingsState = MutableStateFlow<UiState<BookingsData>>(UiState.Loading)
    val bookingsState: StateFlow<UiState<BookingsData>> = _bookingsState.asStateFlow()
    
    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()
    
    fun fetchBookings(tripId: String) {
        viewModelScope.launch {
            bookingRepository.getTripBookings(tripId).collect { result ->
                when (result) {
                    is Resource.Success -> _bookingsState.update { UiState.Success(result.data) }
                    is Resource.Error -> _bookingsState.update { UiState.Error(result.message) }
                    is Resource.Loading -> _bookingsState.update { UiState.Loading }
                }
            }
        }
    }
    
    fun selectTab(index: Int) {
        _selectedTabIndex.update { index }
    }
}
