package com.bangkit23.quizy.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bangkit23.quizy.presentation.navigation.Screen
import com.bangkit23.quizy.presentation.screen.home.HomeScreen
import com.bangkit23.quizy.presentation.screen.leaderboard.LeaderboardScreen
import com.bangkit23.quizy.presentation.screen.login.LoginScreen
import com.bangkit23.quizy.presentation.screen.quiz_play.QuizPlayScreen
import com.bangkit23.quizy.presentation.screen.splash.SplashScreen

@Composable
fun QuizyApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onTimeOut = { isLoggedIn ->
                    if (isLoggedIn) {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Splash.route) {
                                inclusive = true
                            }
                        }
                    } else {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Splash.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            )
        }
        composable(Screen.Login.route) {
            LoginScreen(
                moveToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(
                navigateToLeaderboard = {
                    navController.navigate(Screen.Leaderboard.route)
                },
                navigateToQuizPlay = { quizCategoryId, quizLevelId ->
                    navController.navigate(Screen.QuizPlay.createRoute(quizCategoryId, quizLevelId))
                }
            )
        }
        composable(Screen.Leaderboard.route) {
            LeaderboardScreen(
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
        composable(
            route = Screen.QuizPlay.route,
            arguments = listOf(
                navArgument("quiz_category_id") { type = NavType.StringType },
                navArgument("quiz_level_id") { type = NavType.StringType }
            )
        ) {
            val quizCategoryId = it.arguments?.getString("quiz_category_id")
            val quizLevelId = it.arguments?.getString("quiz_level_id")
            QuizPlayScreen(
                quizCategoryId = quizCategoryId ?: "",
                quizLevelId = quizLevelId ?: "",
                navigateUp = {
                    navController.navigateUp()
                },
                navigateToQuizResultScore = { score ->

                }
            )
        }
    }
}