package com.example.myfinancialleader.home.expense_addition

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun ExpenseAdditionNavController() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "first"
    ) {
        composable("first") {
//            FirstScreen(navController)
        }
        composable("second") {
//            SecondScreen(navController)
        }
        composable("third/{value}") { backStackEntry ->
//            ThirdScreen(
//                navController = navController,
//                value = backStackEntry.arguments?.getString("value") ?: ""
//            )
        }
    }
}