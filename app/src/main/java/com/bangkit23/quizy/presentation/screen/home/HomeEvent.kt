package com.bangkit23.quizy.presentation.screen.home

sealed class HomeEvent {
    data class OnDialogLevelShowHide(
        val isShown: Boolean,
        val quizCategoryId: String? = null,
    ) : HomeEvent()
}