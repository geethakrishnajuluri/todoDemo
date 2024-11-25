package com.sample.todolist.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sample.todolist.presentation.ui.details.DetailsScreen
import com.sample.todolist.presentation.ui.details.DetailsUiState
import com.sample.todolist.presentation.ui.details.DetailsViewModel
import com.sample.todolist.presentation.ui.main.MainScreen
import com.sample.todolist.presentation.ui.main.MainViewModel


object Routes {
    const val MAIN = "main"
    const val DETAILS = "details"
}

@Composable
fun AppNavHost(
    mainViewModel: MainViewModel,
    detailsViewModel: DetailsViewModel,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Routes.MAIN) {
        composable(Routes.MAIN) {
            MainScreen(mainViewModel, navController)
        }
        composable(Routes.DETAILS) {
            DetailsScreen(detailsViewModel, navController = navController)
        }
    }
}