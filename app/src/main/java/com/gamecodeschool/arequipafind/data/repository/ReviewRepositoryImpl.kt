package com.gamecodeschool.arequipafind.data.repository

import com.gamecodeschool.arequipafind.data.remote.firebase.ReviewRemoteDataSource
import com.gamecodeschool.arequipafind.domain.models.Review
import com.gamecodeschool.arequipafind.domain.repository.ReviewRepository
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val remote: ReviewRemoteDataSource
) : ReviewRepository {
    override suspend fun addReview(review: Review): Review = remote.addReview(review)
    override suspend fun getReviewsByUser(userId: String): List<Review> = remote.getReviewsByUser(userId)
}