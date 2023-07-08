package com.bangkit23.quizy.domain.repository

import android.content.Intent
import com.bangkit23.quizy.domain.model.sign_in.SignInResult
import com.bangkit23.quizy.domain.model.user.User
import com.bangkit23.quizy.util.Result
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signInGoogleWithIntent(intent: Intent): Flow<Result<SignInResult>>
    fun getSignedUser(): Flow<User?>
    suspend fun signOut()
}