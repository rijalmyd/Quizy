package com.bangkit23.quizy.presentation.screen.quiz_level

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.bangkit23.quizy.domain.model.quiz.QuizLevel

data class QuizLevelState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val quizLevel: SnapshotStateList<QuizLevel> = mutableStateListOf()
)