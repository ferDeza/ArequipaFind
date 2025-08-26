package com.gamecodeschool.arequipafind.domain.usecases

import com.gamecodeschool.arequipafind.domain.models.Jobs
import com.gamecodeschool.arequipafind.domain.repository.JobRepository
import javax.inject.Inject

class CreateJobUseCase @Inject constructor(
    private val repository: JobRepository
) {
    suspend operator fun invoke(
        ownerId: String,
        title: String,
        description: String,
        finalPrice: Double?
    ): Jobs {
        val now = System.currentTimeMillis()
        val job = Jobs(
            id = "",
            title = title,
            description = description,
            ownerId = ownerId,
            workerId = null,
            finalPrice = finalPrice,
            status = "pending",
            createdAt = now,
            updatedAt = now
        )
        return repository.createJob(job)
    }
}