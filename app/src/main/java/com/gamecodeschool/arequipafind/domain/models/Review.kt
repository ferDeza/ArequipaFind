package com.gamecodeschool.arequipafind.domain.models

import org.w3c.dom.Comment

data class Review (
    val id:String="",
    val jobId:String="",
    val reviewerId:String="",
    val reviewedId:String="",
    val rating:Int=0,
    val skillsWorked: List<String> = emptyList(),
    val comment:String?=null,
    val createdAt:Long = System.currentTimeMillis()

)