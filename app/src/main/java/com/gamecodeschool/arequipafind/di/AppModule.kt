package com.gamecodeschool.arequipafind.di
import com.gamecodeschool.arequipafind.data.remote.firebase.FirebaseAuthDataSource
import com.gamecodeschool.arequipafind.data.repository.AuthRepositoryImpl
import com.gamecodeschool.arequipafind.domain.repository.AuthRepository.AuthRepository
import com.gamecodeschool.arequipafind.domain.usecases.LoginUseCase
import com.gamecodeschool.arequipafind.domain.usecases.RegisterUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import android.content.Context
import com.gamecodeschool.arequipafind.data.remote.firebase.UserRemoteDataSource
import com.gamecodeschool.arequipafind.data.repository.UserRepositoryImpl
import com.gamecodeschool.arequipafind.domain.repository.UserRepository
import com.gamecodeschool.arequipafind.domain.usecases.CheckProfileStatusUseCase
import com.gamecodeschool.arequipafind.domain.usecases.EnsureProfileAfterLoginUseCase
import com.gamecodeschool.arequipafind.domain.usecases.LoginWithGoogleUseCase
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.*
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Firebase core
    @Provides @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    // DataSources
    @Provides @Singleton
    fun provideFirebaseAuthDataSource(firebaseAuth: FirebaseAuth): FirebaseAuthDataSource =
        FirebaseAuthDataSource(firebaseAuth)

    @Provides @Singleton
    fun provideUserRemoteDataSource(firestore: FirebaseFirestore): UserRemoteDataSource =
        UserRemoteDataSource(firestore)

    // Repositories
    @Provides @Singleton
    fun provideAuthRepository(
        authDataSource: FirebaseAuthDataSource,
        firebaseAuth: FirebaseAuth
    ): AuthRepository = AuthRepositoryImpl(authDataSource, firebaseAuth)

    @Provides @Singleton
    fun provideUserRepository(
        remote: UserRemoteDataSource
    ): UserRepository = UserRepositoryImpl(remote)

    // Use cases
    @Provides @Singleton
    fun provideLoginUseCase(repo: AuthRepository): LoginUseCase =
        LoginUseCase(repo)

    @Provides @Singleton
    fun provideRegisterUseCase(
        authRepository: AuthRepository,
        userRepository: UserRepository
    ): RegisterUseCase = RegisterUseCase(authRepository, userRepository)

    @Provides @Singleton
    fun provideLoginWithGoogleUseCase(
        authRepository: AuthRepository,
        userRepository: UserRepository
    ): LoginWithGoogleUseCase = LoginWithGoogleUseCase(authRepository, userRepository)

    @Provides @Singleton
    fun provideCheckProfileStatusUseCase(
        authRepository: AuthRepository,
        userRepository: UserRepository
    ):CheckProfileStatusUseCase = CheckProfileStatusUseCase(authRepository,userRepository)
    @Provides @Singleton
    fun provideEnsureProfileAfterLoginUseCase(
        authRepository: AuthRepository,
        userRepository: UserRepository
    ):EnsureProfileAfterLoginUseCase = EnsureProfileAfterLoginUseCase(authRepository,userRepository)
}
