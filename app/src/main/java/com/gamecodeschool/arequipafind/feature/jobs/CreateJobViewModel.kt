package com.gamecodeschool.arequipafind.feature.jobs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gamecodeschool.arequipafind.domain.models.Jobs
import com.gamecodeschool.arequipafind.domain.repository.AuthRepository.AuthRepository
import com.gamecodeschool.arequipafind.domain.usecases.CreateJobUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateJobViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val createJobUseCase: CreateJobUseCase
):ViewModel(){
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _uiEvent = MutableSharedFlow<CreateJobUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun createJob(title: String, description: String, finalPrice: Double?) {
        val uid = authRepository.currentUserId()
        if (uid == null) {
            viewModelScope.launch { _uiEvent.emit(CreateJobUiEvent.Error("No autenticado")) }
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                createJobUseCase(uid, title, description, finalPrice)
                _uiEvent.emit(CreateJobUiEvent.Saved)
            } catch (e: Exception) {
                _uiEvent.emit(CreateJobUiEvent.Error(e.message ?: "Error creando job"))
            } finally {
                _isLoading.value = false
            }
        }
    }

sealed class CreateJobUiEvent{
        object Saved :CreateJobUiEvent()
        data class Error(val message:String):CreateJobUiEvent()
    }
}