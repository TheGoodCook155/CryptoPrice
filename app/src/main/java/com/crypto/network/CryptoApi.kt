package com.crypto.network

import com.crypto.model.CryptoPrice
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryName
import javax.inject.Singleton

@Singleton
interface CryptoApi {
    @GET("market-price?&rollingAverage=8hours&format=json")
    suspend fun retrieveDataWithParams(@Query("timespan") timespan: String) : CryptoPrice
}