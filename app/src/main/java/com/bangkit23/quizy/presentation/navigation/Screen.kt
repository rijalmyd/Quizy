package com.bangkit23.quizy.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Home : Screen("home")
    object Leaderboard : Screen("leaderboard")
    object QuizPlay : Screen("quiz-play/{quiz_category_id}/{quiz_level_id}") {
        fun createRoute(quizCategoryId: String, quizLevelId: String) = "quiz-play/$quizCategoryId/$quizLevelId"
    }
}
