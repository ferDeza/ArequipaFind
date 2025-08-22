package com.gamecodeschool.arequipafind.domain.usecases
import com.gamecodeschool.arequipafind.domain.repository.UserRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userId: String) = repository.getUserProfile(userId)
}
