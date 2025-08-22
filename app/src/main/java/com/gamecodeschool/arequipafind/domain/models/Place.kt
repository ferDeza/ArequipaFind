package com.gamecodeschool.arequipafind.domain.models

data class Place (
    val id:String,
    val name:String,
    val description:String,
    val category : String,
    val latitude :Double,
    val longitude:Double,
    val imageUrl:String
)