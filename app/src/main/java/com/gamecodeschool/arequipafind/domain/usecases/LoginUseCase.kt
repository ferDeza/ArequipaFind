package com.gamecodeschool.arequipafind.domain.usecases

import android.provider.ContactsContract.CommonDataKinds.Email
import com.gamecodeschool.arequipafind.domain.repository.AuthRepository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email:String,password:String)=
        repository.login(email,password)

}