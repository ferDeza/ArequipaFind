package com.gamecodeschool.arequipafind.domain.usecases

import com.gamecodeschool.arequipafind.domain.models.Jobs
import com.gamecodeschool.arequipafind.domain.repository.JobRepository
import javax.inject.Inject

class AssignJobUseCase @Inject constructor(
    private val repository: JobRepository
) {
    suspend operator fun invoke(jobId: String, workerId: String): Jobs =
        repository.assignJob(jobId, workerId)
}