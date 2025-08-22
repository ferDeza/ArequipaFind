package com.gamecodeschool.arequipafind.domain.usecases

import com.gamecodeschool.arequipafind.domain.repository.AuthRepository.AuthRepository
import com.gamecodeschool.arequipafind.domain.repository.UserRepository
import javax.inject.Inject

class EnsureProfileAfterLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
){
    suspend operator fun invoke(){
        val uid = authRepository.currentUserId()?: throw IllegalStateException("No user after login")
        userRepository.ensureUserProfile(
            userId = uid,
            name = authRepository.currentUserName(),
            email = authRepository.currentUserEmail(),
            photoUrl = authRepository.currentUserPhotoUrl()

        )

    }
}