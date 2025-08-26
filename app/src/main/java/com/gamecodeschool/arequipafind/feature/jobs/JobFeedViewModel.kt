package com.gamecodeschool.arequipafind.feature.jobs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gamecodeschool.arequipafind.domain.models.Jobs
import com.gamecodeschool.arequipafind.domain.usecases.GetJobsFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobFeedViewModel @Inject constructor(
    private val getJobsFeedUseCase: GetJobsFeedUseCase
) :ViewModel(){
    private val _jobs= MutableStateFlow<List<Jobs>>(emptyList())
    val jobs =_jobs.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun loadJobs(){
        viewModelScope.launch {
            _isLoading.value=true
            _error.value=null
        try{
            _jobs.value=getJobsFeedUseCase()
        }catch (e:Exception){
            _error.value = e.message?:"Error de carga de trabajos"
        }finally {
            _isLoading.value=false
            }

        }
    }
}