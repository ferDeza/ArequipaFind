package com.gamecodeschool.arequipafind.feature.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gamecodeschool.arequipafind.domain.usecases.CheckProfileStatusUseCase
import com.gamecodeschool.arequipafind.domain.usecases.EnsureProfileAfterLoginUseCase
import com.gamecodeschool.arequipafind.domain.usecases.LoginUseCase
import com.gamecodeschool.arequipafind.domain.usecases.LoginWithGoogleUseCase
import com.gamecodeschool.arequipafind.domain.usecases.ProfileStatus
import com.gamecodeschool.arequipafind.domain.usecases.RegisterUseCase
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val loginWithGoogleUseCase: LoginWithGoogleUseCase,
    private val ensureProfileAfterLoginUseCase: EnsureProfileAfterLoginUseCase,
    private val checkProfileStatusUseCase: CheckProfileStatusUseCase
) :ViewModel(){
    var uiState=LoginState()
        private set
    fun login(email:String,password:String,onSuccess:()->Unit,onError:(String)->Unit){
        viewModelScope.launch {
            val result = loginUseCase(email,password)
            result.onSuccess { onSuccess() }.onFailure { onError(it.message?:"Error desconocido") }
        }
    }
    fun register(email:String,password:String,onSuccess:()->Unit,onError:(String)->Unit){
        viewModelScope.launch {
            val result = registerUseCase(email,password)
            result.onSuccess { onSuccess() }.onFailure { onError(it.message?:"Error desconocido") }
        }
    }
    fun loginWithGoogle(idToken: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val result = loginWithGoogleUseCase(idToken)
            result.onSuccess { onSuccess() }
                .onFailure { onError(it.message ?: "Error desconocido") }

        }
    }

    fun routeAfterAuth(onGoHome:()->Unit,onGoProfile:()->Unit,onError:(String)->Unit){
        viewModelScope.launch {
            try{
                ensureProfileAfterLoginUseCase()
                when(checkProfileStatusUseCase()){
                    ProfileStatus.COMPLETE->onGoHome()
                    ProfileStatus.INCOMPLETE,ProfileStatus.MISSING->onGoProfile()
                }
            } catch (e:Exception){
                onError(e.message?:"Error post-Auth")
            }
        }
    }
}