package com.subafy.subafy.src.features.auction.di

import com.subafy.subafy.src.features.auction.data.repositories.AuctionRepositoryImpl
import com.subafy.subafy.src.features.auction.domain.repositories.AuctionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuctionModule {

    @Binds
    @Singleton
    abstract fun bindAuctionRepository(
        impl: AuctionRepositoryImpl
    ): AuctionRepository
}