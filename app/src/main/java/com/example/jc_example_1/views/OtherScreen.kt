package com.example.jc_example_1.views

import CustomCenterTopAppBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jc_example_1.models.Const
import com.example.jc_example_1.models.ContactModel
import com.example.jc_example_1.models.User

@Composable
fun OtherScreen(navController: NavHostController, contactModel: ContactModel? = null) {
//    val context = LocalContext.current
//    val viewModel: ShareViewModel = hiltViewModel()
    Scaffold(topBar = {
        CustomCenterTopAppBar(title = "Other Screen", onBackClick = {
//            navController.popBackStack(route = Const.HOME_SCREEN, inclusive = true)

            navController.popBackStack()

        })
    }) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Other Screen!")
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(text = "Hello, ${contactModel?.identityNo} ")
            }

        }
    }
}