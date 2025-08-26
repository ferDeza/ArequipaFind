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
        val now = System.currentTimeMillis()
        val newJob = job.copy(id = doc.id, createdAt = job.createdAt.takeIf { it != 0L } ?: now, updatedAt = now)
        doc.set(newJob).await()
        return newJob
    }

    suspend fun getJobsById(id:String):Jobs?{
        val snap = jobs.document(id).get().await()
        return snap.toObject(Jobs::class.java)

    }
    suspend fun getJobsFeed(): List<Jobs>  =
        jobs.orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .get().await().toObjects(Jobs::class.java)

    suspend fun assignJob(jobId: String, workerId: String): Jobs = firestore.runTransaction { tx ->
        val docRef = jobs.document(jobId)
        val snap = tx.get(docRef)
        val job = snap.toObject(Jobs::class.java) ?: throw IllegalStateException("Job no encontrado")
        if (job.status != "pending") throw IllegalStateException("Job no está en estado 'pending'")
        if (job.ownerId == workerId) throw IllegalStateException("El owner no puede aceptar su propio job")

        val updated = job.copy(
            workerId = workerId,
            status = "assigned",
            updatedAt = System.currentTimeMillis()
        )
        tx.set(docRef, updated)
        updated
    }.await()

    suspend fun completeJob(jobId: String,workerId: String): Jobs = firestore.runTransaction { tx ->
        val docRef = jobs.document(jobId)
        val snap = tx.get(docRef)
        val job = snap.toObject(Jobs::class.java) ?: throw IllegalStateException("Job no encontrado")
        if (job.workerId != workerId) throw IllegalStateException("Solo el worker asignado puede completar el job")
        if (job.status != "assigned") throw IllegalStateException("Job no está en estado 'assigned'")

        val updated = job.copy(
            status = "completed",
            updatedAt = System.currentTimeMillis()
        )
        tx.set(docRef, updated)
        updated
    }.await()
}
