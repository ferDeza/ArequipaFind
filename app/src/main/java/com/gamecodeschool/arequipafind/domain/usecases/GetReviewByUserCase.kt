package com.gamecodeschool.arequipafind.domain.usecases

import com.gamecodeschool.arequipafind.domain.models.Review
import com.gamecodeschool.arequipafind.domain.repository.ReviewRepository
import javax.inject.Inject

class GetReviewByUserCase @Inject constructor(
    private val repository: ReviewRepository
) {
    suspend operator fun invoke(userId: String): List<Review> = repository.getReviewsByUser(userId)
}