package com.bangkit23.quizy.data.repository

import android.content.Intent
import com.bangkit23.quizy.data.firebase.FirebaseService
import com.bangkit23.quizy.domain.repository.AuthRepository
import com.bangkit23.quizy.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val firebaseService: FirebaseService,
) : AuthRepository {

    override fun signInGoogleWithIntent(intent: Intent) = flow {
        emit(Result.Loading())
        try {
            val result = firebaseService.signInGoogleWithIntent(intent)
            emit(Result.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error("Terjadi Kesalahan"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getSignedUser() = flow {
        emit(firebaseService.getSignedUser())
    }.flowOn(Dispatchers.IO)

    override suspend fun signOut() = firebaseService.signOut()
}