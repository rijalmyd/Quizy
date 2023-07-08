package com.bangkit23.quizy.di

import com.bangkit23.quizy.data.repository.AuthRepositoryImpl
import com.bangkit23.quizy.data.repository.QuizRepositoryImpl
import com.bangkit23.quizy.data.repository.UserRepositoryImpl
import com.bangkit23.quizy.domain.repository.AuthRepository
import com.bangkit23.quizy.domain.repository.QuizRepository
import com.bangkit23.quizy.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun provideQuizRepository(quizRepositoryImpl: QuizRepositoryImpl): QuizRepository
}