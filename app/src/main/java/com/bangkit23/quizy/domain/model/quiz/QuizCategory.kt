package com.bangkit23.quizy.domain.model.quiz

data class QuizCategory(
    val id: String = "",
    val image: String = "",
    val title: String = "",
    val totalQuestions: Int = 0,
    @JvmField
    val isNew: Boolean = false,
)
