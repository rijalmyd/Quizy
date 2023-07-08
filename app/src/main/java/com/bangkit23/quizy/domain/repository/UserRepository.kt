package com.bangkit23.quizy.domain.repository

import com.bangkit23.quizy.domain.model.user.User
import com.bangkit23.quizy.util.Result
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserStatistic(): Flow<Result<User?>>
    fun getLeaderboard(): Flow<Result<List<User>>>
}