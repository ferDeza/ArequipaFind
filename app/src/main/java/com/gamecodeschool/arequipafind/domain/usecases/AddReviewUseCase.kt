package com.gamecodeschool.arequipafind.domain.usecases

import com.gamecodeschool.arequipafind.domain.models.Review
import com.gamecodeschool.arequipafind.domain.repository.ReviewRepository
import javax.inject.Inject

class AddReviewUseCase @Inject constructor(
    private val repository: ReviewRepository
) {
    suspend operator fun invoke(jobId: String, reviewerId: String, reviewedId: String, rating: Int, comment: String?): Review {
        val review = Review(
            jobId = jobId,
            reviewerId = reviewerId,
            reviewedId = reviewedId,
            rating = rating,
            comment = comment
        )
        return repository.addReview(review)
    }
}