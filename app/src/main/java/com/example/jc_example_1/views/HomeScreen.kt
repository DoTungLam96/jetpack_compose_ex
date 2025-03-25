package com.example.jc_example_1.views

import CustomCenterTopAppBar
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jc_example_1.models.Routes
import com.example.jc_example_1.models.User
import com.example.jc_example_1.viewmodels.ShareViewModel

@Composable
fun HomeScreen(navController: NavHostController, user: User? = null, viewModel: ShareViewModel = hiltViewModel()) {
    val context = LocalContext.current
    Scaffold(topBar = {
        CustomCenterTopAppBar(title = "Home",
            onBackClick = {
                navController.popBackStack()
            },

            actions = {
            Row {
                IconButton(onClick = {
                    Toast.makeText(
                        context, "Bạn vừa bấm menu", Toast.LENGTH_SHORT
                    ).show()
                }) {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }

                IconButton(onClick = {
                    Toast.makeText(
                        context, "Bạn vừa bấm Add", Toast.LENGTH_SHORT
                    ).show()
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
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(
                            0xFFFF9800
                        )
                    ), // Màu cam
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(52.dp),
                    onClick = {

                       // navController.currentBackStackEntry?.savedStateHandle?.set("user", user)

                      val user =  viewModel.user;

                        navController.navigate(Routes.DETAIL_SCREEN)
                    },

                    ) {
                    Text(text = "Go to DetailScreen")
                }
            }

        }
    }
}