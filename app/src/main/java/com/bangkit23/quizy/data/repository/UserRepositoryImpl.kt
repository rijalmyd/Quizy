package com.bangkit23.quizy.data.repository

import com.bangkit23.quizy.data.firebase.FirebaseService
import com.bangkit23.quizy.domain.repository.UserRepository
import com.bangkit23.quizy.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val firebaseService: FirebaseService,
) : UserRepository {

    override fun getUserStatistic() = flow {
        emit(Result.Loading())
        try {
            val result = firebaseService.getUserStatistic()
            emit(Result.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error("Terjadi Kesalahan"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getLeaderboard() = flow {
        emit(Result.Loading())
        try {
            val result = firebaseService.getLeaderboard()
            emit(Result.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message))
        }
    }.flowOn(Dispatchers.IO)
}