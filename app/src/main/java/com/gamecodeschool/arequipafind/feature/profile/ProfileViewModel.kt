package com.gamecodeschool.arequipafind.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gamecodeschool.arequipafind.domain.models.UserProfile
import com.gamecodeschool.arequipafind.domain.repository.AuthRepository.AuthRepository
import com.gamecodeschool.arequipafind.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _profile = MutableStateFlow<UserProfile?>(null)
    val profile: StateFlow<UserProfile?> = _profile

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    private val _uiEvent = MutableSharedFlow<ProfileUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        loadProfile()
    }
    fun currentUid(): String? = authRepository.currentUserId()

    fun loadProfile() {
        val uid = currentUid()?: return
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val profile = userRepository.getUserProfile(uid)
                _profile.value = profile
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateProfile(updated: UserProfile) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value=null
            try {
                userRepository.upsertUserProfile(updated) // tu impl actual
                _profile.value = updated
                _uiEvent.emit(ProfileUiEvent.Saved)
            } catch (e: Exception) {
                _error.value = e.message
                _uiEvent.emit(ProfileUiEvent.Error(e.message ?: "Error guardando perfil"))
            } finally {
                _isLoading.value = false
            }
        }
    }
    sealed class ProfileUiEvent {
        object Saved : ProfileUiEvent()
        data class Error(val message: String) : ProfileUiEvent()
    }
}