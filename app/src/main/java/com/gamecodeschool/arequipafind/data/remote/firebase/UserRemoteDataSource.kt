package com.gamecodeschool.arequipafind.data.remote.firebase

import com.gamecodeschool.arequipafind.domain.models.UserProfile
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
class UserRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val users = firestore.collection("users")

    suspend fun getUserProfile(uid: String): UserProfile? {
        val doc = users.document(uid).get().await()
        return doc.toObject(UserProfile::class.java)
    }

    suspend fun upsert(profile: UserProfile) {
        val toSave = profile.copy(updatedAt = System.currentTimeMillis())
        users.document(profile.id).set(toSave, SetOptions.merge()).await()
    }
}
