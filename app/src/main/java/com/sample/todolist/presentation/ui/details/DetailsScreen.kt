package com.sample.todolist.presentation.ui.details

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    val ctx = LocalContext.current

    var textInput by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val coroutineScope = rememberCoroutineScope()

    // Reset state when the screen is displayed
    LaunchedEffect(Unit) {
        viewModel.resetState()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TextField(
            value = textInput,
            onValueChange = { textInput = it },
            placeholder = { Text("Enter TODO item") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (textInput.text.isNotBlank()) {
                    viewModel.addTodo(textInput.text)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add TODO")
        }


        LaunchedEffect(uiState) {
            if (uiState.success) {
                navController.navigate("main") {

                    popUpTo("main") { inclusive = true }
                }
            } else if (uiState.errorMessage != null) {
                coroutineScope.launch {
                    navController.navigate("main") {
                        popUpTo("main") { inclusive = true }
                    }
                    Toast.makeText(ctx, "Failed to add TODO: ${uiState.errorMessage}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    if (uiState.isLoading) {
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f))
            .clickable { }
            .zIndex(1f)
        ){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.Green,
                strokeWidth = 3.dp
            )
        }
    }
}
