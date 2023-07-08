package com.bangkit23.quizy.presentation.screen.leaderboard

import com.bangkit23.quizy.domain.model.user.User

data class LeaderboardState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val listLeaderboard: List<User> = emptyList(),
)