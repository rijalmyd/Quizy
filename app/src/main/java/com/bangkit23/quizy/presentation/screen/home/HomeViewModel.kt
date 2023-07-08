package com.bangkit23.quizy.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit23.quizy.domain.repository.QuizRepository
import com.bangkit23.quizy.domain.repository.UserRepository
import com.bangkit23.quizy.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val quizRepository: QuizRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        getData()
        getQuizCategories()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnDialogLevelShowHide -> _state.update {
                it.copy(
                    isDialogLevelShown = event.isShown,
                    quizCategoryId = event.quizCategoryId
                )
            }
        }
    }

    private fun getData() = viewModelScope.launch {
        userRepository.getUserStatistic().collect { result ->
            when (result) {
                is Result.Error -> _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }
                is Result.Loading -> _state.update {
                    it.copy(
                        isLoading = true,
                    )
                }
                is Result.Success -> _state.update {
                    it.copy(
                        isLoading = false,
                        user = result.data
                    )
                }
            }
        }
    }

    private fun getQuizCategories() = viewModelScope.launch {
        quizRepository.getQuizCategories().collect { result ->
            when (result) {
                is Result.Error -> _state.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
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
                        listQuiz = result.data
                    )
                }
            }
        }
    }
}