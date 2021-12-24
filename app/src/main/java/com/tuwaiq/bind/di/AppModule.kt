package com.tuwaiq.bind.di

import android.app.Application
import android.content.Context
import com.tuwaiq.bind.data.repos.AuthRepoImp
import com.tuwaiq.bind.data.repos.FeedsRepoImpl
import com.tuwaiq.bind.data.repos.LocationRepoImpl
import com.tuwaiq.bind.domain.repos.AuthRepo
import com.tuwaiq.bind.domain.repos.FeedsRepo
import com.tuwaiq.bind.domain.repos.LocationRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext application: Context): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideFeedsRepo(context: Application): FeedsRepo = FeedsRepoImpl(context)

    @Provides
    @Singleton
    fun provideLocationRepo(context: Application): LocationRepo = LocationRepoImpl(context)

    @Provides
    @Singleton
    fun provideAuthRepo(): AuthRepo = AuthRepoImp()

}