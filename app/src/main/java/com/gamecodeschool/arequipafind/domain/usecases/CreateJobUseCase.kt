package com.gamecodeschool.arequipafind.domain.usecases

import com.gamecodeschool.arequipafind.domain.models.Jobs
import com.gamecodeschool.arequipafind.domain.repository.JobRepository
import javax.inject.Inject

class CreateJobUseCase @Inject constructor(
    private val repository: JobRepository
) {
    suspend operator fun invoke(ownerId: String, title: String, description: String): Jobs {
        val job = Jobs(ownerId = ownerId, title = title, description = description)
        return repository.createdJob(job)
    }
}