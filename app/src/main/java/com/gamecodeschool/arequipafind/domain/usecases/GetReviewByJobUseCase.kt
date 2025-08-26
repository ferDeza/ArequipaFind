package com.gamecodeschool.arequipafind.domain.usecases;

import com.gamecodeschool.arequipafind.domain.models.Review
import com.gamecodeschool.arequipafind.domain.repository.ReviewRepository
import javax.inject.Inject;

class GetReviewByJobUseCase @Inject constructor(
        private val repository: ReviewRepository
) {
        suspend operator fun invoke (jobId:String):List<Review> = repository.getReviewsByJob(jobId)
}
