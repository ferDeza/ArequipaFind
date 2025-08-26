package com.gamecodeschool.arequipafind.domain.repository

import com.gamecodeschool.arequipafind.domain.models.Jobs

interface JobRepository {
    suspend fun createJob(job:Jobs):Jobs
    suspend fun getJobsFeed():List<Jobs>
    suspend fun getJobsById(id:String):Jobs?
    suspend fun assignJob(jobId:String,workerId:String): Jobs
    suspend fun completeJob(jobId:String,workerId: String):Jobs
}