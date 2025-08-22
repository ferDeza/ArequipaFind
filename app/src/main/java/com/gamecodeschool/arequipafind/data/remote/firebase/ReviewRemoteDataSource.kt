package com.gamecodeschool.arequipafind.data.remote.firebase

import com.gamecodeschool.arequipafind.domain.models.Review
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ReviewRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val reviews = firestore.collection("reviews")

    suspend fun addReview(review: Review): Review {
        val doc = reviews.document()
        val newReview = review.copy(id = doc.id)
        doc.set(newReview).await()
        return newReview
    }

    suspend fun getReviewsByUser(userId: String): List<Review> =
        reviews.whereEqualTo("reviewedId", userId)
            .get().await()
            .toObjects(Review::class.java)
}