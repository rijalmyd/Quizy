package com.bangkit23.quizy.presentation.screen.login

import android.content.Intent

sealed class LoginEvent {
    data class OnSignInGoogleWithIntent(
        val intent: Intent
    ) : LoginEvent()
    data class SetLoadingState(
        val isLoading: Boolean
    ) : LoginEvent()
    object ResetState : LoginEvent()
}