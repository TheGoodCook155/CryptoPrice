package com.crypto.viewmodel

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crypto.data.Data
import com.crypto.model.CryptoPrice
import com.crypto.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(private val repository: Repository) : ViewModel(){

    val dataWithParams : MutableState<Data<CryptoPrice, Boolean, Exception>> =
        mutableStateOf(Data(null,true,Exception("")))

     fun getDataWithParams(timeSpan : String) : Data<CryptoPrice, Boolean, Exception> {

        viewModelScope.launch {

            dataWithParams.value.loading = true;

            dataWithParams.value = repository.getCryptoPriceWithParams(timeSpan) //Returns Data Wrapper

            if (dataWithParams.value.data?.values!!.isNotEmpty()){
                dataWithParams.value.loading = false;
            }else{
                dataWithParams.value.loading = true
            }
        }

        return dataWithParams.value

    }

}