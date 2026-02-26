package com.subafy.subafy.src.features.dashboard.di

import com.subafy.subafy.src.features.dashboard.data.datasource.remote.api.AuctionWsApi
import com.subafy.subafy.src.features.dashboard.data.datasource.remote.api.AuctionWsApiImpl
import com.subafy.subafy.src.features.dashboard.data.repositories.AuctionRepositoryDetailImpl
import com.subafy.subafy.src.features.dashboard.domain.repositories.AuctionRepositoryDetail
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AuctionDetailModule {

    @Binds
    @Singleton
    abstract fun bindAuctionWsApi(
        impl: AuctionWsApiImpl
    ): AuctionWsApi

    @Binds
    @Singleton
    abstract fun bindAuctionRepositoryDetail(
        impl: AuctionRepositoryDetailImpl
    ): AuctionRepositoryDetail
}