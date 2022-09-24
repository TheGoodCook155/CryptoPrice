package com.crypto.screens

import android.util.Log
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.animateTo
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import com.crypto.R
import com.crypto.navigation.Screens
import com.crypto.network.isInternetAvailable
import com.crypto.viewmodel.CryptoViewModel

@Composable
fun SplashScreen(navController: NavController) {

    Log.d("cycle", "SplashScreen: starts")

    val rotation = remember {
        Animatable(0f)
    }

    val context = LocalContext.current
    val hasInternet = isInternetAvailable(context)

    LaunchedEffect(key1 = true){

        rotation.animateTo(targetValue = 360f, animationSpec = tween(durationMillis = 2000,
            easing = {
                OvershootInterpolator(10f)
                    .getInterpolation(it)
            })
        )

        delay(1500L)
        Log.d("cycle", "SplashScreen: end")

        navController.navigate(Screens.PICK_SCREEN.name)

    }

    if (hasInternet){

        androidx.compose.material.Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Transparent, shape = RectangleShape),
            shape = RectangleShape

        ) {

            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Application logo", modifier = Modifier
                    .width(235.dp)
                    .height(255.dp)
                    .rotate(rotation.value))
            }

        }

    }else{
        Toast.makeText(context,
            "Check your internet connection: Exiting",
            Toast.LENGTH_SHORT
        ).show()
    }




}