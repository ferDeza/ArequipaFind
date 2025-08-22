package com.gamecodeschool.arequipafind.domain.repository

import com.gamecodeschool.arequipafind.domain.models.Place

interface PlaceRepository {
    suspend fun getPlaces(): Result<List<Place>>
    suspend fun getPlaceById(id: String): Result<Place>
}