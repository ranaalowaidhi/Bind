package com.tuwaiq.bind.di


import com.tuwaiq.bind.data.repos.AuthRepoImp
import com.tuwaiq.bind.domain.repos.AuthRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepo():AuthRepo = AuthRepoImp()

}