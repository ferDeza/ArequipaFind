package com.gamecodeschool.arequipafind.core.di

import com.gamecodeschool.arequipafind.data.repository.EventRepositoryImpl
import com.gamecodeschool.arequipafind.data.repository.PlaceRepositoryImpl
import com.gamecodeschool.arequipafind.data.repository.UserRepositoryImpl
import com.gamecodeschool.arequipafind.domain.repository.EventRepository
import com.gamecodeschool.arequipafind.domain.repository.PlaceRepository
import com.gamecodeschool.arequipafind.domain.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent
/*@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideUserRepository(impl: UserRepositoryImpl): UserRepository = impl

    @Provides
    @Singleton
    fun providePlaceRepository(impl: PlaceRepositoryImpl): PlaceRepository = impl

    @Provides
    @Singleton
    fun provideEventRepository(impl: EventRepositoryImpl): EventRepository = impl
}*/