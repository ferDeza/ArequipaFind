package com.gamecodeschool.arequipafind.feature.jobs

import androidx.lifecycle.ViewModel
import com.gamecodeschool.arequipafind.domain.models.Jobs
import com.gamecodeschool.arequipafind.domain.repository.AuthRepository.AuthRepository
import com.gamecodeschool.arequipafind.domain.usecases.AssignJobUseCase
import com.gamecodeschool.arequipafind.domain.usecases.CompleteJobUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.gamecodeschool.arequipafind.domain.usecases.GetJobByIdUseCase
import kotlinx.coroutines.launch

@HiltViewModel
class JobDetailViewModel @Inject constructor(
    private val getJobByIdUseCase: GetJobByIdUseCase,
    private val assignJobUseCase: AssignJobUseCase,
    private val completeJobUseCase: CompleteJobUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _job = MutableStateFlow<Jobs?>(null)
    val job = _job.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _uiEvent = MutableSharedFlow<JobUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun load(jobId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _job.value = getJobByIdUseCase(jobId)
            } catch (e: Exception) {
                _uiEvent.emit(JobUiEvent.Error(e.message ?: "Error cargando trabajo"))
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun acceptJob() {
        val uid = authRepository.currentUserId() ?: run {
            viewModelScope.launch { _uiEvent.emit(JobUiEvent.Error("No autenticado")) }; return
        }
        val currentJob = _job.value ?: run {
            viewModelScope.launch { _uiEvent.emit(JobUiEvent.Error("Job no cargado")) }; return
        }
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val updated = assignJobUseCase(currentJob.id, uid)
                _job.value = updated
                _uiEvent.emit(JobUiEvent.Assigned)
            } catch (e: Exception) {
                _uiEvent.emit(JobUiEvent.Error(e.message ?: "Error al aceptar"))
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun completeJob() {
        val uid = authRepository.currentUserId() ?: run {
            viewModelScope.launch { _uiEvent.emit(JobUiEvent.Error("No autenticado")) }; return
        }
        val currentJob = _job.value ?: run {
            viewModelScope.launch { _uiEvent.emit(JobUiEvent.Error("Job no cargado")) }; return
        }
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val updated = completeJobUseCase(currentJob.id, uid)
                _job.value = updated
                _uiEvent.emit(JobUiEvent.Completed)
            } catch (e: Exception) {
                _uiEvent.emit(JobUiEvent.Error(e.message ?: "Error al completar"))
            } finally {
                _isLoading.value = false
            }
        }
    }

    sealed class JobUiEvent {
        object Assigned : JobUiEvent()
        object Completed : JobUiEvent()
        data class Error(val message: String) : JobUiEvent()
    }
}