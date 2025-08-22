package com.gamecodeschool.arequipafind.domain.usecases

import com.gamecodeschool.arequipafind.domain.models.Jobs
import com.gamecodeschool.arequipafind.domain.repository.JobRepository
import javax.inject.Inject

class GetJobsFeedUseCase @Inject constructor(
    private val repository: JobRepository
) {
    suspend operator fun invoke(): List<Jobs> = repository.getJobsFeed()
}