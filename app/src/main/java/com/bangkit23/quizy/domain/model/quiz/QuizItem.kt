package com.bangkit23.quizy.domain.model.quiz

data class QuizItem(
    val id: String = "",
    val question: String = "",
    val choices: List<String> = emptyList(),
    val correctAnswer: String = "",
    val quizType: String = "",
)
