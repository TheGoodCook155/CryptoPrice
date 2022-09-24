package com.crypto.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.crypto.R
import com.crypto.navigation.Screens
import java.util.*
import java.util.regex.Pattern

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun PickScreen(navController: NavHostController = rememberNavController()) {


    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var spanPeriod = remember {
        mutableStateOf("")
    }

    var spanValue = remember {
        mutableStateOf("")
    }

        Surface(modifier = Modifier.fillMaxSize(), color = Color.Transparent) {

            Column(
                Modifier.padding(15.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Application logo", modifier = Modifier
                    .width(100.dp)
                    .height(100.dp))

                    Divider(modifier = Modifier.padding(15.dp))

                    Text(text = "Please choose a time span period (days/weeks/years)", modifier = Modifier.padding(10.dp))


               DropDownMenu {
                   spanPeriod.value = it
               }

                Log.d("spanPeriod", "PickScreen: After method finishes: ${spanPeriod.value}")

                    Text(text = "Please choose a time span number value (5,7,10,200...)", modifier = Modifier.padding(10.dp))
                val validInput = remember {
                    mutableStateOf(false)
                }
                var regex = "^[1-9][0-9]*\$"
                val pattern = Pattern.compile(regex)

                TextField(
                        value = spanValue.value,
                        onValueChange = {

                            spanValue.value = it

                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            Log.d("onDone", "PickScreen: INVOKED")
                            if (spanValue.value.matches(pattern.toRegex())){
                                validInput.value = true
                            }else{
                                Toast.makeText(context,"Invalid input",Toast.LENGTH_SHORT).show()
                                validInput.value = false
                            }
                            keyboardController?.hide()

                        }) {

                            keyboardController?.hide()
                        }
                    )

                Button(enabled = validInput.value,

                    onClick = {

                    val combined = spanValue.value.toString()+spanPeriod.value.trim()
                    Log.d("valuesSent", "PickScreen: Values ${combined}")
                    val route = Screens.MAIN_SCREEN.name

                    navController.navigate("$route/${combined}")


                }, modifier = Modifier.padding(30.dp)) {

                    Text(text = "Create crypto graph")

                }

            }
        }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownMenu(toReturn : (String) -> Unit) {

    val listItems = arrayOf("days", "weeks", "years")

    var selectedItem by remember {
        mutableStateOf(listItems.get(0))
    }
    Log.d("selectedItem", "DropDownMenu: After Init: ${listItems.get(0).toString()}")

    var expanded by remember {
        mutableStateOf(false)
    }

    // the box
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {

        // text field
        TextField(
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            label = {
                Text(text = "Choose period")
                    },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )

        // menu
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            listItems.forEach { selectedOption ->
                // menu item
                Log.d("selectedItem", "DropDownMenu: Before creating fields: ${selectedItem.toString()}")

                DropdownMenuItem(onClick = {
                    Log.d("selectedItem", "DropDownMenu: After Init")

                    selectedItem = selectedOption
                        expanded = false
                        toReturn(selectedItem)
                        Log.d("selectedItem", "DropDownMenu: ${selectedItem.toLowerCase(Locale.ENGLISH)}")

                }) {
                    Text(text = selectedOption)
                }
            }
        }
    }
    toReturn(selectedItem)
}

