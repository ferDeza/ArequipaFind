package com.gamecodeschool.arequipafind.domain.repository

import com.gamecodeschool.arequipafind.domain.models.Event

interface EventRepository {
    suspend fun getEvents(): Result<List<Event>>
    suspend fun getEventsByPlace(placeId: String): Result<List<Event>>
}