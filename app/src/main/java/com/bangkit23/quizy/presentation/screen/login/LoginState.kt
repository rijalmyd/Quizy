package com.bangkit23.quizy.presentation.screen.login

import com.bangkit23.quizy.domain.model.sign_in.SignInResult

data class LoginState(
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val isSignInSuccessful: Boolean = false,
    val signInResult: SignInResult? = null,
)
