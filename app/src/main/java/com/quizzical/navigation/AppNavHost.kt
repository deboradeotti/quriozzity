package com.quizzical.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.quizzical.presentation.start.view.StartScreen
import com.quizzical.presentation.quiz.view.QuizScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "start") {
        composable(route="start") { StartScreen(onClickStart = { navController.navigate("quiz")}) }
        composable("quiz") { QuizScreen() }
    }
}