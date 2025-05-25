package com.example.mytravel.ui.theme.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytravel.ui.theme.data.model.User
import com.example.mytravel.ui.theme.data.repository.UserRepository
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
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _userState = MutableStateFlow<UiState<User>>(UiState.Loading)
    val userState: StateFlow<UiState<User>> = _userState.asStateFlow()
    
    private val _showShareCodeDialog = MutableStateFlow(false)
    val showShareCodeDialog: StateFlow<Boolean> = _showShareCodeDialog.asStateFlow()
    
    fun fetchUserProfile(userId: String) {
        viewModelScope.launch {
            userRepository.getUserProfile(userId).collect { result ->
                when (result) {
                    is Resource.Success -> _userState.update { UiState.Success(result.data) }
                    is Resource.Error -> _userState.update { UiState.Error(result.message) }
                    is Resource.Loading -> _userState.update { UiState.Loading }
                }
            }
        }
    }
    
    fun showShareCode() {
        _showShareCodeDialog.update { true }
    }
    
    fun hideShareCode() {
        _showShareCodeDialog.update { false }
    }
}
