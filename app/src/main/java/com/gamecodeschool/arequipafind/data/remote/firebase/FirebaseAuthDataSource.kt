package com.gamecodeschool.arequipafind.data.remote.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

//Comunicacion con firebase
class FirebaseAuthDataSource (
    private val firebaseAuth: FirebaseAuth ){
    suspend fun login(email:String, password:String):Result<Unit> {   return try {
        firebaseAuth.signInWithEmailAndPassword(email,password).await()
        Result.success(Unit)
    } catch (e:Exception){
        Result.failure(e)
    }
    }
    suspend fun register(email:String, password:String):Result<Unit> {   return try {
        firebaseAuth.createUserWithEmailAndPassword(email,password).await()
        Result.success(Unit)
    } catch (e:Exception){
        Result.failure(e)
    }
    }
    suspend fun loginWithGoogle(idToken: String): Result<Unit> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            firebaseAuth.signInWithCredential(credential).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    fun logout(){
       firebaseAuth.signOut()
   }
    fun isUserLoggedIn():Boolean{
        return firebaseAuth.currentUser !=null
    }
}