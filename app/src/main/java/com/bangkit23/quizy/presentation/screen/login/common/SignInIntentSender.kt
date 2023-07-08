package com.bangkit23.quizy.presentation.screen.login.common

import android.content.Context
import android.content.IntentSender
import com.bangkit23.quizy.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.tasks.await

suspend fun signInIntentSender(context: Context): IntentSender? {
    return try {
        val onTapClient = Identity.getSignInClient(context)
        val result = onTapClient.beginSignIn(
            buildSignInRequest(context)
        ).await()
        result?.pendingIntent?.intentSender
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

private fun buildSignInRequest(context: Context): BeginSignInRequest {
    return BeginSignInRequest.Builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(context.getString(R.string.web_client_id))
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()
}