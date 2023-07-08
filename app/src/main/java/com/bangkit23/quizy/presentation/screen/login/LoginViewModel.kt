package com.bangkit23.quizy.presentation.screen.login

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit23.quizy.domain.repository.AuthRepository
import com.bangkit23.quizy.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnSignInGoogleWithIntent -> {
                signInGoogleWithIntent(event.intent)
            }
            is LoginEvent.SetLoadingState -> _state.update {
                it.copy(
                    isLoading = event.isLoading
                )
            }
            is LoginEvent.ResetState -> _state.update {
                LoginState()
            }
        }
    }

    private fun signInGoogleWithIntent(intent: Intent) = viewModelScope.launch {
        authRepository.signInGoogleWithIntent(intent).collect {  result ->
            when (result) {
                is Result.Error -> _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = result.message,
                        isSignInSuccessful = false
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
                        isSignInSuccessful = true,
                        signInResult = result.data
                    )
                }
            }
        }
    }
}