package com.gamecodeschool.arequipafind.data.repository
import com.gamecodeschool.arequipafind.domain.models.Event
import com.gamecodeschool.arequipafind.data.remote.firebase.EventRemoteDataSource
import com.gamecodeschool.arequipafind.domain.repository.EventRepository
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val remote: EventRemoteDataSource
) : EventRepository {

    override suspend fun getEvents(): Result<List<Event>> = remote.getEvents()
    override suspend fun getEventsByPlace(placeId: String): Result<List<Event>> =
        remote.getEventsByPlace(placeId)
}