package com.gamecodeschool.arequipafind.data.remote.firebase

import com.gamecodeschool.arequipafind.domain.models.Event
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class EventRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val eventsCollection = firestore.collection("events")

    suspend fun getEvents(): Result<List<Event>> {
        return try {
            val snapshot = eventsCollection.get().await()
            val events = snapshot.toObjects(Event::class.java)
            Result.success(events)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getEventsByPlace(placeId: String): Result<List<Event>> {
        return try {
            val snapshot = eventsCollection.whereEqualTo("placeId", placeId).get().await()
            val events = snapshot.toObjects(Event::class.java)
            Result.success(events)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}