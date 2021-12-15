package com.tuwaiq.bind.di

import android.app.Application
import android.content.Context
import com.tuwaiq.bind.data.repos.FeedsRepoImpl
import com.tuwaiq.bind.data.repos.LocationRepoImpl
import com.tuwaiq.bind.domain.repos.FeedsRepo
import com.tuwaiq.bind.domain.repos.LocationRepo
import com.tuwaiq.bind.domain.use_cases.feeds_use_cases.GetLocationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeedsModule {

    @Provides
    @Singleton
    fun provideFeedsRepo():FeedsRepo = FeedsRepoImpl()

    @Provides
    @Singleton
    fun provideLocationRepo(context:Application):LocationRepo = LocationRepoImpl(context)


}