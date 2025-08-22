package com.gamecodeschool.arequipafind.data.repository

import com.gamecodeschool.arequipafind.data.remote.firebase.UserRemoteDataSource
import com.gamecodeschool.arequipafind.domain.models.UserProfile
import com.gamecodeschool.arequipafind.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remote: UserRemoteDataSource
) : UserRepository {

    override suspend fun getUserProfile(userId: String): UserProfile? =
        remote.getUserProfile(userId)

    override suspend fun upsertUserProfile(profile: UserProfile) =
        remote.upsert(profile)

    override suspend fun ensureUserProfile(
        userId: String,
        name: String?,
        email: String?,
        photoUrl: String?
    ): UserProfile {
        val existing = remote.getUserProfile(userId)
        if (existing != null) return existing

        val profile = UserProfile(
            id = userId,
            name = name ?: "",
            email = email,
            photoUrl = photoUrl
        )
        remote.upsert(profile)
        return profile
    }
}