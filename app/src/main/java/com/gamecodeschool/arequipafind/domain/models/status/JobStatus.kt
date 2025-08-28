package com.gamecodeschool.arequipafind.domain.models.status

enum class JobStatus (val value:String){
    PENDING("pending"),
    ASSIGNED ("assigned"),
    COMPLETE("complete");

    companion object{
        fun fromValue(value:String):JobStatus?=values().find { it.value == value }
    }

}