package com.gamecodeschool.arequipafind.domain.repository.AuthRepository

import android.provider.ContactsContract.CommonDataKinds.Email

interface AuthRepository {
    suspend fun login(email: String,password:String):Result<Unit>
    suspend fun register(email: String,password:String):Result<Unit>
    fun logout()
    fun isUserLoggedIn():Boolean
    suspend fun loginWithGoogle(idToken:String):Result<Unit>
//Caso para el perfil de usuario
    fun currentUserId(): String?
    fun currentUserEmail(): String?
    fun currentUserName(): String?
    fun currentUserPhotoUrl(): String?
}