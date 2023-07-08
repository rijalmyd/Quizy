package com.bangkit23.quizy.presentation.screen.quiz_play

sealed class QuizPlayEvent {
    data class FetchAllQuiz(
        val quizCategoryId: String,
        val quizLevelId: String
    ) : QuizPlayEvent()
    data class OnAnswered(
        val selectedChoice: String,
    ) : QuizPlayEvent()
    object MoveToNext : QuizPlayEvent()
}
