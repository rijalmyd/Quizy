package com.bangkit23.quizy.domain.model.sign_in

import com.bangkit23.quizy.domain.model.user.User

data class SignInResult(
    val user: User?,
    val errorMessage: String?
)
