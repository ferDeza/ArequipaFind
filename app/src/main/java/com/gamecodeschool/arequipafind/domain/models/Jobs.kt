package com.gamecodeschool.arequipafind.domain.models

import com.gamecodeschool.arequipafind.domain.models.status.JobStatus

data class Jobs (
    val id:String="",
    val title:String="",
    val description:String="",
    val ownerId:String="",
    val workerId:String?=null,
    val status:String="pending",
    val requiredSkill: String? = null,
    val requiredSkillLevel: Int? = null,
    val budget: Double? = null,      // opcional para owner
    val finalPrice: Double? = null,
    val createdAt:Long=System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

