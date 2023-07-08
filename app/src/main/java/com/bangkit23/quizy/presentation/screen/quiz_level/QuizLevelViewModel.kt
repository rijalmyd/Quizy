package com.bangkit23.quizy.presentation.screen.quiz_level

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit23.quizy.domain.repository.QuizRepository
import com.bangkit23.quizy.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizLevelViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(QuizLevelState())
    val state = _state.asStateFlow()

    fun onEvent(event: QuizLevelEvent) {
        when (event) {
            is QuizLevelEvent.OnGetQuizLevel -> getQuizLevel(event.quizCategoryId)
            is QuizLevelEvent.ResetState -> _state.update { QuizLevelState() }
        }
    }

    private fun getQuizLevel(quizCategoryId: String) = viewModelScope.launch {
        quizRepository.getQuizLevel(quizCategoryId).collect { result ->
            when (result) {
                is Result.Error -> _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }
                is Result.Loading -> _state.update {
                    it.copy(
                        isLoading = true
                    )
                }
                is Result.Success -> _state.update {
                    it.copy(
                        isLoading = false,
                        quizLevel = result.data.toMutableStateList()
                    )
                }
            }
        }
    }
}