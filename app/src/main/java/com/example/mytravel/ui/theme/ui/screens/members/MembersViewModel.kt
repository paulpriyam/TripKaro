package com.example.mytravel.ui.theme.ui.screens.members

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytravel.ui.theme.data.model.TripMember
import com.example.mytravel.ui.theme.data.repository.MemberRepository
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
class MembersViewModel @Inject constructor(
    private val memberRepository: MemberRepository
) : ViewModel() {
    
    private val _membersState = MutableStateFlow<UiState<List<TripMember>>>(UiState.Loading)
    val membersState: StateFlow<UiState<List<TripMember>>> = _membersState.asStateFlow()
    
    private val _addMemberState = MutableStateFlow<UiState<TripMember>?>(null)
    val addMemberState: StateFlow<UiState<TripMember>?> = _addMemberState.asStateFlow()
    
    private val _showAddDialog = MutableStateFlow(false)
    val showAddDialog: StateFlow<Boolean> = _showAddDialog.asStateFlow()
    
    fun fetchMembers(tripId: String) {
        viewModelScope.launch {
            memberRepository.getTripMembers(tripId).collect { result ->
                when (result) {
                    is Resource.Success -> _membersState.update { UiState.Success(result.data) }
                    is Resource.Error -> _membersState.update { UiState.Error(result.message) }
                    is Resource.Loading -> _membersState.update { UiState.Loading }
                }
            }
        }
    }
    
    fun addMember(tripId: String, email: String) {
        viewModelScope.launch {
            _addMemberState.update { UiState.Loading }
            memberRepository.addTripMember(tripId, email).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _addMemberState.update { UiState.Success(result.data) }
                        _showAddDialog.update { false }
                        // Refresh member list
                        fetchMembers(tripId)
                    }
                    is Resource.Error -> _addMemberState.update { UiState.Error(result.message) }
                    is Resource.Loading -> _addMemberState.update { UiState.Loading }
                }
            }
        }
    }
    
    fun showAddMemberDialog() {
        _showAddDialog.update { true }
    }
    
    fun hideAddMemberDialog() {
        _showAddDialog.update { false }
    }
    
    fun resetAddMemberState() {
        _addMemberState.update { null }
    }
}
