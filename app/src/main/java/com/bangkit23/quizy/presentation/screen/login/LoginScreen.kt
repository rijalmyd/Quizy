package com.bangkit23.quizy.presentation.screen.login

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bangkit23.quizy.R
import com.bangkit23.quizy.presentation.components.LoadingDialog
import com.bangkit23.quizy.presentation.screen.login.common.signInIntentSender
import com.bangkit23.quizy.presentation.ui.theme.QuizyTheme
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    moveToHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == RESULT_OK) {
                scope.launch {
                    viewModel.onEvent(LoginEvent.OnSignInGoogleWithIntent(result.data ?: return@launch))
                }
            }
        }
    )

    LoginContent(
        onSignInClick = {
            viewModel.onEvent(LoginEvent.SetLoadingState(true))
            scope.launch {
                val signInIntentSender = signInIntentSender(context.applicationContext)
                viewModel.onEvent(LoginEvent.SetLoadingState(false))
                launcher.launch(
                    IntentSenderRequest.Builder(
                        signInIntentSender ?: return@launch
                    ).build()
                )
            }
        }
    )

    if (state.isLoading) {
        LoadingDialog()
    }

    LaunchedEffect(state.isSignInSuccessful) {
        if (state.isSignInSuccessful) {
            moveToHome()
            viewModel.onEvent(LoginEvent.ResetState)
        }
    }
}

@Composable
fun LoginContent(
    onSignInClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.studying))
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.TopStart)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .offset(y = (-16).dp)
        ) {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(256.dp)
            )
            Spacer(Modifier.height(24.dp))
            Text(
                text = "Uji kemampuan\nberfikirmu!",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                fontFamily = FontFamily(
                    Font(R.font.poppins_medium)
                )
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Quizy adalah game asah otak dengan ratusan soal yang menantang.",
                textAlign = TextAlign.Center,
            )
        }
        GoogleSignInButton(
            onSignInClick = onSignInClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun GoogleSignInButton(
    onSignInClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onSignInClick,
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
        modifier = modifier.padding(8.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_google),
            contentDescription = "Masuk dengan Google",
            tint = Color.Unspecified,
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text("Masuk dengan Google")
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun LoginContentPreview() {
    QuizyTheme {
        LoginContent(
            onSignInClick = {}
        )
    }
}