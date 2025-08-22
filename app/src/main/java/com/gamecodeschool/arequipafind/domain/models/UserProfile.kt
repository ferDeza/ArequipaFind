package com.gamecodeschool.arequipafind.domain.models

data class UserProfile(
    val id:String="",
    val name:String?=null,
    val email :String?=null,
    val photoUrl: String? = null,
    val role:String="contratista",
    val skills:List<String> = emptyList(),
    val rating :Double=0.0,
    val reviewsCount:Int= 0,
    val updatedAt: Long = System.currentTimeMillis()
)