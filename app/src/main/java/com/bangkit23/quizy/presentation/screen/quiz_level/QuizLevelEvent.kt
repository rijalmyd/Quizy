package com.bangkit23.quizy.presentation.screen.quiz_level

sealed class QuizLevelEvent {
    data class OnGetQuizLevel(val quizCategoryId: String) : QuizLevelEvent()
    object ResetState : QuizLevelEvent()
}
