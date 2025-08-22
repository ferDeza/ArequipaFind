package com.gamecodeschool.arequipafind.domain.repository

import com.gamecodeschool.arequipafind.domain.models.Review

interface ReviewRepository {
    suspend fun addReview(review: Review): Review
    suspend fun getReviewsByUser(userId: String): List<Review>
}