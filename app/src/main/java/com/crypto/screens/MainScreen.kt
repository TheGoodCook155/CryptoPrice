package com.crypto.screens


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.crypto.data.Data
import com.crypto.model.CryptoPrice
import com.crypto.network.isInternetAvailable
import com.crypto.utils.formatDate
import com.crypto.viewmodel.CryptoViewModel
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@SuppressLint("CoroutineCreationDuringComposition", "UnrememberedMutableState")
@Composable
fun MainScreen (navController: NavController, param : String, viewModel: CryptoViewModel = hiltViewModel() ){
//
    Log.d("paramInMain", "MainScreen: starts ${param} ")


    val context = LocalContext.current
    var cryptoWrapper : MutableState<Data<CryptoPrice, Boolean, Exception>> =
        mutableStateOf(Data(null,true,Exception("")))

            Log.d("paramInMain", "MainScreen: BEFORE PASSING ")
            cryptoWrapper.value = viewModel.getDataWithParams(param)

            if (cryptoWrapper.value.data != null){
                cryptoWrapper.value.loading = false
                Log.d("getDataWithParams", "MainScreen: cryptoWrapper.value.loading:  ${cryptoWrapper.value.loading} ")

            }

            Log.d("getDataWithParams", "MainScreen: DATA  ${cryptoWrapper.value.data} ")
            Log.d("getDataWithParams", "MainScreen: Loading ${cryptoWrapper.value.loading} ")


    val cryptoOBJ = cryptoWrapper.value.data


//
    Log.d("DATA", "MainScreen: CryptoOBJ ${cryptoOBJ}")



    if (cryptoWrapper.value.loading == true){

            Column(modifier = Modifier.padding(5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
            }

        }else{
            LineGraphComp(cryptoOBJ)
        }

    Log.d("cycle", "MainScreen: ends ")


}
@Preview
@Composable
fun LineGraphComp(cryptoObj: CryptoPrice? = null) {

    Log.d("cycle", "LineGraphComp: starts")
    val context = LocalContext.current
    val cryptoValues = mutableListOf<DataPoint>()
    val cryptoDates = mutableListOf<String>()

    var counter = 0
    val dateToBeShown = remember {
        mutableStateOf("")
    }

    val cryptoValueToBeShown = remember {
        mutableStateOf(0)
    }

    Log.d("size", "LineGraphComp: ${cryptoObj!!.values.size}")
    for (item in cryptoObj!!.values){
        counter++
        Log.d("cryptoValues", "LineGraphComp: ${item} ")

        var x: Float = item.x.toFloat()
        var y: Float = item.y.toFloat()

        var dataPoint = DataPoint(counter.toFloat(),y)
        cryptoDates.add(formatDate(item.x).toString())
        cryptoValues.add(dataPoint)
        Log.d("cryptoValues", "LineGraphComp:  to DATE  ${formatDate(item.x)}")
    }
    Log.d("cryptoValues", "LineGraphComp: ${cryptoValues.size}")

    val stepsMod = remember {
        mutableStateOf(0)
    }

    if (cryptoObj!!.values.size < 100){
        stepsMod.value = 20
    }else if (cryptoObj!!.values.size >= 100 && cryptoObj!!.values.size <= 1000){
        stepsMod.value = 30
    }else if (cryptoObj!!.values.size >= 1000 && cryptoObj!!.values.size <= 10000){
        stepsMod.value = 40
    }else{
        stepsMod.value = 60
    }

    Row(modifier = Modifier
        .padding(2.dp)
        .fillMaxWidth()
        .fillMaxHeight(),

            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {

        LineGraph(
            plot = LinePlot(
                grid = LinePlot.Grid(Color.Gray.copy(alpha = 0.20f)),
                lines = listOf(
                    element = LinePlot.Line(
                        dataPoints = cryptoValues,
                        connection = LinePlot.Connection(Color.Black, 3.dp),
                        intersection = null,
                    ),
                ),
                xAxis = LinePlot.XAxis(
                    steps = cryptoObj!!.values.size,
//TODO
//                    stepSize = 20.dp,
                    stepSize = stepsMod.value.dp,


                ),
                yAxis = LinePlot.YAxis(
                    steps = 10,
                )
            ),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ,
            onSelectionStart = {

            },
            onSelection = {f,listof ->
                Log.d("f", "LineGraphComp: ${f}, listOF ${listof}")
                var dataDayItem = listof.get(0).x
                dateToBeShown.value = cryptoDates.get(dataDayItem.toInt()-1)
                cryptoValueToBeShown.value = listof.get(0).y.toInt()
                Log.d("day", "LineGraphComp: ${dateToBeShown}")

            },
            onSelectionEnd = {
                Toast.makeText(context,
                    "${dateToBeShown.value}: Price: ${cryptoValueToBeShown.value} ${cryptoObj.unit} ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
    }

    Log.d("cycle", "LineGraphComp: ends")



}