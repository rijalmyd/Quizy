package com.bangkit23.quizy.presentation.screen.quiz_play

import com.bangkit23.quizy.domain.model.quiz.QuizItem

data class QuizPlayState(
    val isLoading: Boolean = false,
    val quizItem: QuizItem = QuizItem(),
    val choices: List<String> = emptyList(),
    val totalQuiz: Int = 0,
    val isAllQuizFinished: Boolean = false,
    val selectedChoice: String = "",
    val answerState: Answer = Answer.NONE,
    val isCorrect: Boolean = false,
    val position: Int = 1,
    val timer: Int = 10,
    val eventCountDownFinish: Boolean = false,
)

enum class Answer {
    NONE,
    CORRECT,
    WRONG,
}