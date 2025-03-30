package com.example.jc_example_1.AppModule

import DataStoreManager
import android.content.Context
import com.example.jc_example_1.repository.AuthRepository
import com.example.jc_example_1.repository.AuthRepositoryImpl
import com.example.jc_example_1.repository.UserRepository
import com.example.jc_example_1.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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

