package com.bangkit23.quizy.data.firebase

import android.content.Intent
import com.bangkit23.quizy.domain.model.quiz.QuizCategory
import com.bangkit23.quizy.domain.model.quiz.QuizItem
import com.bangkit23.quizy.domain.model.quiz.QuizLevel
import com.bangkit23.quizy.domain.model.sign_in.SignInResult
import com.bangkit23.quizy.domain.model.user.User

interface FirebaseService {
    suspend fun signInGoogleWithIntent(intent: Intent): SignInResult
    suspend fun signOut()
    suspend fun getSignedUser(): User?
    suspend fun getUserStatistic(): User?
    suspend fun addPoints(points: Int)
    suspend fun getLeaderboard(): List<User>
    suspend fun getQuizCategories(): List<QuizCategory>
    suspend fun getQuizLevel(quizCategoryId: String): List<QuizLevel>
    suspend fun getQuizQuestions(quizCategoryId: String, quizLevelId: String): List<QuizItem>
}