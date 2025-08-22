package com.gamecodeschool.arequipafind.domain.usecases

import com.gamecodeschool.arequipafind.domain.models.UserProfile
import com.gamecodeschool.arequipafind.domain.repository.UserRepository

class EnsureUserProfileUseCase (
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        userId: String,
        name: String?,
        email: String?,
        photoUrl: String?
    ): UserProfile {
        return userRepository.ensureUserProfile(userId, name, email, photoUrl)
    }
}