package com.gamecodeschool.arequipafind.domain.repository

import com.gamecodeschool.arequipafind.domain.models.UserProfile

interface UserRepository {
    suspend fun getUserProfile(userId: String): UserProfile?
    suspend fun upsertUserProfile(profile: UserProfile)
    suspend fun ensureUserProfile(
        userId: String,
        name: String?,
        email: String?,
        photoUrl: String?
    ): UserProfile
}