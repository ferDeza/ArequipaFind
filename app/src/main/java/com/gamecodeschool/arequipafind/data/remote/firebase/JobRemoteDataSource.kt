package com.gamecodeschool.arequipafind.data.remote.firebase

import com.gamecodeschool.arequipafind.domain.models.Jobs
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class JobRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val jobs = firestore.collection("jobs")

    suspend fun createJob(job: Jobs): Jobs {
        val doc = jobs.document()
        val newJob = job.copy(id = doc.id)
        doc.set(newJob).await()
        return newJob
    }

    suspend fun getJobsFeed(): List<Jobs> =
        jobs.get().await().toObjects(Jobs::class.java)

    suspend fun assignJob(jobId: String, workerId: String): Jobs {
        val doc = jobs.document(jobId)
        doc.update(mapOf("workerId" to workerId, "status" to "assigned")).await()
        return doc.get().await().toObject(Jobs::class.java)!!
    }

    suspend fun completeJob(jobId: String): Jobs {
        val doc = jobs.document(jobId)
        doc.update("status", "completed").await()
        return doc.get().await().toObject(Jobs::class.java)!!
    }
}