package com.bangkit23.quizy.presentation.screen.quiz_play

import android.os.CountDownTimer
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit23.quizy.domain.model.quiz.QuizItem
import com.bangkit23.quizy.domain.repository.QuizRepository
import com.bangkit23.quizy.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizPlayViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(QuizPlayState())
    val state = _state.asStateFlow()

    private val allQuiz = mutableStateListOf<QuizItem>()
    private var timer: CountDownTimer? = null

    init {
        startTimer()
    }

    fun onEvent(event: QuizPlayEvent) {
        when (event) {
            is QuizPlayEvent.FetchAllQuiz -> getAllQuiz(event.quizCategoryId, event.quizLevelId)
            is QuizPlayEvent.OnAnswered -> {
                _state.update {
                    val isCorrect = event.selectedChoice == allQuiz[it.position - 1].correctAnswer
                    it.copy(
                        selectedChoice = event.selectedChoice,
                        isCorrect = isCorrect,
                        answerState = if (isCorrect) Answer.CORRECT else Answer.WRONG,
                    )
                }
                resetTimer()
            }
            is QuizPlayEvent.MoveToNext -> {
                _state.update {
                   if (it.position < allQuiz.size) {
                       val quizItem = allQuiz[it.position]
                       it.copy(
                           isLoading = false,
                           quizItem = quizItem,
                           choices = quizItem.choices.shuffled(),
                           totalQuiz = allQuiz.size,
                           position = it.position + 1,
                       )
                   } else {
                       it.copy(
                           isAllQuizFinished = true,
                       )
                   }
                }
                resetState()
                startTimer()
            }
        }
    }

    private fun resetState() {
        _state.update {
            it.copy(
                isLoading = false,
                selectedChoice = "",
                answerState = Answer.NONE,
                isCorrect = false,
            )
        }
    }

    private fun getAllQuiz(quizCategoryId: String, quizLevelId: String) = viewModelScope.launch {
        quizRepository.getQuizQuestions(quizCategoryId, quizLevelId).collect { result ->
            when (result) {
                is Result.Error -> _state.update {
                    it.copy(
                        isLoading = false,
                    )
                }

                is Result.Loading -> _state.update {
                    it.copy(
                        isLoading = true
                    )
                }

                is Result.Success -> _state.update {
                    val quiz = result.data.shuffled()
                    allQuiz.addAll(quiz)
                    val quizItem = allQuiz[it.position - 1]
                    it.copy(
                        isLoading = false,
                        quizItem = quizItem,
                        choices = quizItem.choices.shuffled(),
                        totalQuiz = allQuiz.size
                    )
                }
            }
        }
    }

    private fun startTimer() {
        timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _state.update {
                    it.copy(
                        timer = millisUntilFinished.toInt() / 1000
                    )
                }
            }

            override fun onFinish() {
                resetTimer()
            }
        }
        timer?.start()
    }

    private fun resetTimer() {
        timer?.cancel()
        _state.update {
            it.copy(
                eventCountDownFinish = true
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}