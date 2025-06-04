package com.quriozzity.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.quriozzity.presentation.about.view.AboutScreen
import com.quriozzity.presentation.start.view.StartScreen
import com.quriozzity.presentation.quiz.view.QuizScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "start") {
        composable(route="start") {
            StartScreen(
                onClickStart = { navController.navigate("quiz") },
                onClickAbout = { navController.navigate("about") }
            )
        }
        composable(route = "about") { AboutScreen { navController.navigateUp() } }
        composable(route = "quiz") { QuizScreen() }
    }
}