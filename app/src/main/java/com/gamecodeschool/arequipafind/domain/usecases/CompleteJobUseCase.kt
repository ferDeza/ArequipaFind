package com.gamecodeschool.arequipafind.domain.usecases

import com.gamecodeschool.arequipafind.domain.models.Jobs
import com.gamecodeschool.arequipafind.domain.repository.JobRepository
import javax.inject.Inject

class CompleteJobUseCase @Inject constructor(
    private val repository: JobRepository
) {
    suspend operator fun invoke(jobId: String): Jobs = repository.completeJob(jobId)
}