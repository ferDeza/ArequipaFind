package com.gamecodeschool.arequipafind.domain.usecases

import com.gamecodeschool.arequipafind.domain.repository.AuthRepository.AuthRepository
import com.gamecodeschool.arequipafind.domain.repository.UserRepository
import javax.inject.Inject

class LoginWithGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(idToken: String): Result<Unit> {
        val r = authRepository.loginWithGoogle(idToken)
        return r.mapCatching {
            val uid = authRepository.currentUserId()
                ?: throw IllegalStateException("Usuario no disponible tras login")
            userRepository.ensureUserProfile(
                userId = uid,
                name = authRepository.currentUserName(),
                email = authRepository.currentUserEmail(),
                photoUrl = authRepository.currentUserPhotoUrl()
            )
            Unit
        }
    }
}