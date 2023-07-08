package com.bangkit23.quizy.data.repository

import com.bangkit23.quizy.data.firebase.FirebaseService
import com.bangkit23.quizy.domain.repository.QuizRepository
import com.bangkit23.quizy.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizRepositoryImpl @Inject constructor(
    private val firebaseService: FirebaseService,
) : QuizRepository {

    override fun getQuizCategories() = flow {
        emit(Result.Loading())
        try {
            val result = firebaseService.getQuizCategories()
            emit(Result.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message))
        }
    }.flowOn(Dispatchers.IO)

    override fun getQuizLevel(quizCategoryId: String) = flow {
        emit(Result.Loading())
        try {
            val result = firebaseService.getQuizLevel(quizCategoryId)
            emit(Result.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message))
        }
    }.flowOn(Dispatchers.IO)

    override fun getQuizQuestions(quizCategoryId: String, quizLevelId: String) = flow {
        emit(Result.Loading())
        try {
            val result = firebaseService.getQuizQuestions(quizCategoryId, quizLevelId)
            emit(Result.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message))
        }
    }.flowOn(Dispatchers.IO)
}