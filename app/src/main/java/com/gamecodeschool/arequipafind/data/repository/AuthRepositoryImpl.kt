package com.gamecodeschool.arequipafind.data.repository

import com.gamecodeschool.arequipafind.data.remote.firebase.FirebaseAuthDataSource
import com.gamecodeschool.arequipafind.domain.repository.AuthRepository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuthDataSource: FirebaseAuthDataSource,
    private val firebaseAuth: FirebaseAuth
): AuthRepository {
    //Login
    override suspend fun login(email:String, password:String)=
        firebaseAuthDataSource.login(email,password)
    //Registro
    override suspend fun register(email:String,password: String)=
        firebaseAuthDataSource.register(email, password)
    //Login con Google
    override suspend fun loginWithGoogle(idToken: String) =
        firebaseAuthDataSource.loginWithGoogle(idToken)
    //Logoout
    override fun logout()=
        firebaseAuthDataSource.logout()
    //Si esta logeado
    override fun isUserLoggedIn()=
        firebaseAuthDataSource.isUserLoggedIn()
    //Para el perfil de usuario
    override fun currentUserId(): String? = firebaseAuth.currentUser?.uid
    override fun currentUserEmail(): String? = firebaseAuth.currentUser?.email
    override fun currentUserName(): String? = firebaseAuth.currentUser?.displayName
    override fun currentUserPhotoUrl(): String? = firebaseAuth.currentUser?.photoUrl?.toString()

}