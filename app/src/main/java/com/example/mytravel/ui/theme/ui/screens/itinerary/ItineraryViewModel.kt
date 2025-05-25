package com.example.mytravel.ui.theme.ui.screens.itinerary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytravel.ui.theme.data.model.ItineraryItem
import com.example.mytravel.ui.theme.data.repository.ItineraryRepository
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
class ItineraryViewModel @Inject constructor(
    private val itineraryRepository: ItineraryRepository
) : ViewModel() {
    
    private val _itineraryState = MutableStateFlow<UiState<Map<Int, List<ItineraryItem>>>>(UiState.Loading)
    val itineraryState: StateFlow<UiState<Map<Int, List<ItineraryItem>>>> = _itineraryState.asStateFlow()
    
    private val _selectedDay = MutableStateFlow(1)
    val selectedDay: StateFlow<Int> = _selectedDay.asStateFlow()
    
    fun fetchItinerary(tripId: String) {
        viewModelScope.launch {
            itineraryRepository.getTripItinerary(tripId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        // Group itinerary items by day
                        val groupedByDay = result.data.groupBy { it.day }
                        _itineraryState.update { UiState.Success(groupedByDay) }
                    }
                    is Resource.Error -> _itineraryState.update { UiState.Error(result.message) }
                    is Resource.Loading -> _itineraryState.update { UiState.Loading }
                }
            }
        }
    }
    
    fun setSelectedDay(day: Int) {
        _selectedDay.update { day }
    }
}
