package com.subafy.subafy.src.core.di

import com.google.gson.Gson
import com.subafy.subafy.BuildConfig
import com.subafy.subafy.src.features.auth.data.repositories.AuthWsApiImpl
import com.subafy.subafy.src.features.auth.data.datasource.remote.api.AuthHttpApi
import com.subafy.subafy.src.features.auth.data.datasource.remote.api.AuthWsApi
import com.subafy.subafy.src.features.dashboard.data.datasource.remote.api.DashboardHttpApi
import com.subafy.subafy.src.features.auction.data.datasource.remote.api.AuctionHttpApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.HTTP_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthHttpApi(retrofit: Retrofit): AuthHttpApi {
        return retrofit.create(AuthHttpApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDashboardHttpApi(retrofit: Retrofit): DashboardHttpApi {
        return retrofit.create(DashboardHttpApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthWsApi(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): AuthWsApi {
        return AuthWsApiImpl(okHttpClient, gson)
    }

    @Provides
    @Singleton
    fun provideAuctionHttpApi(retrofit: Retrofit): AuctionHttpApi {
        return retrofit.create(AuctionHttpApi::class.java)
    }
}