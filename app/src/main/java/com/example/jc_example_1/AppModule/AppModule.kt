package com.example.jc_example_1.AppModule

import com.example.jc_example_1.repository.AuthRepository
import com.example.jc_example_1.repository.AuthRepositoryImpl
import com.example.jc_example_1.repository.UserRepository
import com.example.jc_example_1.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository
}