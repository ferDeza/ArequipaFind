package com.gamecodeschool.arequipafind.domain.usecases

import com.gamecodeschool.arequipafind.domain.models.UserProfile
import com.gamecodeschool.arequipafind.domain.repository.AuthRepository.AuthRepository
import com.gamecodeschool.arequipafind.domain.repository.UserRepository
import javax.inject.Inject

enum class ProfileStatus {
    MISSING,INCOMPLETE,COMPLETE
}
class CheckProfileStatusUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke():ProfileStatus{
        val uid=authRepository.currentUserId()?: return ProfileStatus.MISSING
        val p = userRepository.getUserProfile(uid)?: return  ProfileStatus.MISSING
        return if (isComplete(p)) ProfileStatus.COMPLETE else ProfileStatus.INCOMPLETE
    }

    private fun isComplete(p:UserProfile):Boolean{
        val validRole = p.role=="contratista" || p.role== "trabajador"
        return !p.name.isNullOrBlank() && validRole && p.skills.isNotEmpty()
    }


}