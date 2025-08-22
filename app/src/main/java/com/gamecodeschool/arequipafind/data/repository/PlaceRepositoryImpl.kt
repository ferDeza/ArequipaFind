package com.gamecodeschool.arequipafind.data.repository

import com.gamecodeschool.arequipafind.data.remote.firebase.PlaceRemoteDataSource
import com.gamecodeschool.arequipafind.domain.models.Place
import com.gamecodeschool.arequipafind.domain.repository.PlaceRepository
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val remote: PlaceRemoteDataSource
) : PlaceRepository {

    override suspend fun getPlaces(): Result<List<Place>> = remote.getPlaces()

    override suspend fun getPlaceById(id: String): Result<Place> = remote.getPlaceById(id)
}