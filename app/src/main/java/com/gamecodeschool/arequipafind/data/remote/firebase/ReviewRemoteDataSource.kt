package com.gamecodeschool.arequipafind.data.remote.firebase

import com.gamecodeschool.arequipafind.domain.models.Jobs
import com.gamecodeschool.arequipafind.domain.models.Review
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ReviewRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val reviews = firestore.collection("reviews")
    private val users = firestore.collection("users")
    private val jobs = firestore.collection("jobs")


    suspend fun addReview(review: Review): Review = firestore.runTransaction { tx ->
        // 1) verificar job
        val jobRef = jobs.document(review.jobId)
        val jobSnap = tx.get(jobRef)
        val job = jobSnap.toObject(Jobs::class.java)
            ?: throw IllegalStateException("Job no encontrado")
        if (job.status != "completed") throw IllegalStateException("No se puede reseñar: job no está completado")

        // 2) crear review
        val reviewRef = reviews.document()
        val now = System.currentTimeMillis()
        val reviewToSave = review.copy(id = reviewRef.id, createdAt = now)
        tx.set(reviewRef, reviewToSave)

        // 3) actualizar rating global y reviewsCount del usuario reseñado
        val userRef = users.document(review.reviewedId)
        val userSnap = tx.get(userRef)
        val currentRating = userSnap.getDouble("rating") ?: 0.0
        val currentCount = userSnap.getLong("reviewsCount")?.toInt() ?: 0
        val newCount = currentCount + 1
        val newRating = if (currentCount == 0) {
            review.rating.toDouble()
        } else {
            (currentRating * currentCount + review.rating) / newCount
        }
        tx.update(userRef, mapOf("rating" to newRating, "reviewsCount" to newCount))

        reviewToSave
    }.await()

    suspend fun getReviewsByUser(userId: String): List<Review> =
        reviews.whereEqualTo("reviewedId", userId)
            .get().await()
            .toObjects(Review::class.java)


    suspend fun getReviewsByJob(jobId: String): List<Review> =
        reviews.whereEqualTo("jobId", jobId)
            .get().await()
            .toObjects(Review::class.java)
}