package com.bangkit23.quizy.domain.repository

import com.bangkit23.quizy.domain.model.quiz.QuizCategory
import com.bangkit23.quizy.domain.model.quiz.QuizItem
import com.bangkit23.quizy.domain.model.quiz.QuizLevel
import com.bangkit23.quizy.util.Result
import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    fun getQuizCategories(): Flow<Result<List<QuizCategory>>>
    fun getQuizLevel(quizCategoryId: String): Flow<Result<List<QuizLevel>>>
    fun getQuizQuestions(quizCategoryId: String, quizLevelId: String): Flow<Result<List<QuizItem>>>
}