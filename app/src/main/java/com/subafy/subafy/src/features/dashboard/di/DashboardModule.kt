package com.subafy.subafy.src.features.dashboard.di

import com.subafy.subafy.src.features.dashboard.data.repositories.DashboardRepositoryImpl
import com.subafy.subafy.src.features.dashboard.domain.repositories.DashboardRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DashboardModule {

    @Binds
    @Singleton
    abstract fun bindDashboardRepository(
        dashboardRepositoryImpl: DashboardRepositoryImpl
    ): DashboardRepository
}