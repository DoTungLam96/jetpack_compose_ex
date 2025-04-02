package com.example.jc_example_1.views

import CustomCenterTopAppBar
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jc_example_1.models.Const
import com.example.jc_example_1.models.User
import com.example.jc_example_1.viewmodels.DetailEvent
import com.example.jc_example_1.viewmodels.DetailState
import com.example.jc_example_1.viewmodels.DetailViewModel
import com.example.jc_example_1.viewmodels.DetailUIState
import com.example.jc_example_1.viewmodels.HomeViewModel

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun DetailScreen(
    navController: NavHostController,
    user: User? = null,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsState()

    LaunchedEffect(state) {
            if (state.isInitial){

                viewModel.onEvent(DetailEvent.onInit(user = user))
            }
            if(!state.errorMessage.isNullOrBlank()){
                Toast.makeText(context, state.errorMessage!!, Toast.LENGTH_SHORT).show()
            }
    }

//    LaunchedEffect(detailUIState) {
//        when (detailUIState) {
//            is DetailUIState.Init,  DetailUIState.Loading -> {
//            }
//
//            is DetailUIState.Error -> {
//                Toast.makeText(context, detailUIState.errorMessage, Toast.LENGTH_LONG).show()
//            }
//            else -> {}
//        }
//    }

    Scaffold(topBar = {
        CustomCenterTopAppBar(title = "Detail", onBackClick = {
            navController.popBackStack()
            viewModel.resetState()
        })
    }) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (state.isLoading ) {
                    CircularProgressIndicator()
                }

                if(!state.errorMessage.isNullOrBlank()){
                    Text(text = "${state.errorMessage}", color = Color.Red)
                }

                 if(state.contactModel != null && state.errorMessage.isNullOrBlank()) {
                    Text(text = "Welcome ${state.contactModel?.identityNo}")
                }
                Button(
                    modifier = Modifier.padding(top = 16.dp),
                    onClick = {
//                        viewModel.updateContact()
                    navController.navigate(Const.OTHER_SCREEN)

                    }) {
                    Text(text = "Go to Others")
                }
            }

        }
    }
}