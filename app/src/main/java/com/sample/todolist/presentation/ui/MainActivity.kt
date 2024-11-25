package com.sample.todolist.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sample.todolist.presentation.navigation.AppNavHost
import com.sample.todolist.presentation.ui.details.DetailsScreen
import com.sample.todolist.presentation.ui.details.DetailsViewModel
import com.sample.todolist.presentation.ui.main.MainScreen
import com.sample.todolist.presentation.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private val detailsViewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoApp(mainViewModel, detailsViewModel)
        }
    }
}

@Composable
fun TodoApp(mainViewModel: MainViewModel, detailsViewModel: DetailsViewModel) {
    val navController = rememberNavController()
    MaterialTheme {
        AppNavHost(mainViewModel, detailsViewModel, navController)
    }
}

