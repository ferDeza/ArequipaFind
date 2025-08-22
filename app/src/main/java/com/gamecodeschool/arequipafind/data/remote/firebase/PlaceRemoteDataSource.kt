package com.gamecodeschool.arequipafind.data.remote.firebase

import com.gamecodeschool.arequipafind.domain.models.Place
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PlaceRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val placesCollection = firestore.collection("places")

    suspend fun getPlaces(): Result<List<Place>> {
        return try {
            val snapshot = placesCollection.get().await()
            val places = snapshot.toObjects(Place::class.java)
            Result.success(places)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getPlaceById(id: String): Result<Place> {
        return try {
            val snapshot = placesCollection.document(id).get().await()
            val place = snapshot.toObject(Place::class.java)
            if (place != null) Result.success(place) else Result.failure(Exception("No encontrado"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}