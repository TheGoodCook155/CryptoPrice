package com.crypto.repository

import android.util.Log
import com.crypto.data.Data
import com.crypto.model.CryptoPrice
import com.crypto.network.CryptoApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class Repository @Inject constructor(private val api : CryptoApi) {


    private val dataOrException = Data<CryptoPrice,Boolean,Exception>()

    suspend fun getCryptoPriceWithParams(timeSpan : String): Data<CryptoPrice,Boolean,Exception>{
        Log.d("paramInRepo", "getCryptoPriceWithParams: ${timeSpan}")
        Log.d("getCryptoPriceWithParams", "Repository | getCryptoPriceWithParams: ")
        dataOrException.loading = true;
        try {
            dataOrException.data = api.retrieveDataWithParams(timeSpan) //Crypto price Obj
            if (dataOrException.data != null){
                dataOrException.loading = false
            }
        }catch (ex: Exception){
            dataOrException.exception = ex
            Log.e("ERROR", "Repository | getCryptoPriceWithParams() | ${ex.localizedMessage.toString()} ")
        }
        Log.d("getCryptoPriceWithParams", "Repository | DATA | ${dataOrException.data?.values.toString()} ")
        Log.d("getCryptoPriceWithParams", "Repository | getCryptoPriceWithParams: EXITING")

        return dataOrException
    }



}