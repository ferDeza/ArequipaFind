package com.gamecodeschool.arequipafind.domain.models

data class Event (
    val id:String,
    val title:String,
    val description:String,
    val date :Long,
    val placeId:String
)