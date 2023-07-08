package com.bangkit23.quizy.presentation.screen.home

import com.bangkit23.quizy.domain.model.quiz.QuizCategory
import com.bangkit23.quizy.domain.model.user.User

data class HomeState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val user: User? = null,
    val listQuiz: List<QuizCategory> = emptyList(),
    val isDialogLevelShown: Boolean = false,
    val quizCategoryId: String? = null,
)