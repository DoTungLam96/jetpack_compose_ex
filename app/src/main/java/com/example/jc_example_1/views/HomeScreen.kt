package com.example.jc_example_1.views

import CustomCenterTopAppBar
import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jc_example_1.models.Const
import com.example.jc_example_1.models.User
import com.example.jc_example_1.viewmodels.HomeEvent
import com.example.jc_example_1.viewmodels.HomeState
import com.example.jc_example_1.viewmodels.HomeViewModel
import com.google.gson.Gson

@SuppressLint("UnrememberedGetBackStackEntry", "SuspiciousIndentation", "UnrememberedMutableState",
    "StateFlowValueCalledInComposition"
)
@Composable
fun HomeScreen(
    navController: NavHostController,

    viewModel: HomeViewModel = hiltViewModel()

) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit){
        viewModel.onEvent(HomeEvent.onGetUserInfo)
    }

    LaunchedEffect(state) {
        if(state is HomeState.Error){
            Toast.makeText(context, (state as HomeState.Error).errorMessage, Toast.LENGTH_LONG ).show()
        }
        if(state is HomeState.NavHome){

            val userJson = Uri.encode(Gson().toJson((state as HomeState.NavHome).user))

            navController.navigate("${Const.DETAIL_SCREEN}/$userJson")

            viewModel.resetState()
        }
    }

    Scaffold(topBar = {
        CustomCenterTopAppBar(title = "Home",
            actions = {
                Row {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "Menu",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = {
                    }) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add",
                            tint = Color.White,

                            )
                    }

                }
            })
    }) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), color = MaterialTheme.colorScheme.background
        ) {

                Column (
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,

                ){

                    val homeUIState = state

                    Spacer(modifier = Modifier.height(16.dp))

                    if(homeUIState is  HomeState.Loading){
                        CircularProgressIndicator()
                    }
                    if( homeUIState is HomeState.Error){
                        buildBody(message = homeUIState.errorMessage ?: "", onClick = {
                            Toast.makeText(context, homeUIState.errorMessage, Toast.LENGTH_LONG ).show()
                        })
                    }
                    if(homeUIState is HomeState.Success){
                        buildBody(message = "Hi, ${homeUIState.user?.fullName}", onClick = {
                            viewModel.onEvent(HomeEvent.onContinue)
                        })

                    }
                }
            }
        }
}

@Composable
private fun buildBody( message: String?, onClick : (() -> Unit)? = null  ){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF03A9F4)
            ), // Màu cam
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.height(52.dp),
            onClick = {
                onClick?.invoke()
            },

            ) {
            Text(text = "Go to Detail")

        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = message ?: "")
    }
}