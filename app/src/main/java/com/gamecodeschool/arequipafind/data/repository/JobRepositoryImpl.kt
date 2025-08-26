package com.gamecodeschool.arequipafind.data.repository

import com.gamecodeschool.arequipafind.data.remote.firebase.JobRemoteDataSource
import com.gamecodeschool.arequipafind.domain.models.Jobs
import com.gamecodeschool.arequipafind.domain.repository.JobRepository
import javax.inject.Inject

class JobRepositoryImpl @Inject constructor(
    private val remote: JobRemoteDataSource
) : JobRepository {
    override suspend fun createJob(job: Jobs): Jobs = remote.createJob(job)
    override suspend fun getJobsFeed(): List<Jobs> = remote.getJobsFeed()
    override suspend fun getJobsById(id:String):Jobs? = remote.getJobsById(id)
    override suspend fun assignJob(jobId: String, workerId: String): Jobs = remote.assignJob(jobId, workerId)
    override suspend fun completeJob(jobId: String, workerId: String): Jobs = remote.completeJob(jobId,workerId)
}