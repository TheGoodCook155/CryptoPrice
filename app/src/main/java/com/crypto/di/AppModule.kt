package com.crypto.di

import com.crypto.network.CryptoApi
import com.google.android.gms.common.internal.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCryptoApi(): CryptoApi{
        return Retrofit.Builder()
            .baseUrl(com.crypto.constants.Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(CryptoApi::class.java)
    }

}