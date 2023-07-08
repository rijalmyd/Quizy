package com.bangkit23.quizy.presentation.screen.quiz_play

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bangkit23.quizy.domain.model.quiz.QuizItem
import com.bangkit23.quizy.presentation.components.ItemQuizChoice
import com.bangkit23.quizy.presentation.components.LoadingDialog
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun QuizPlayScreen(
    quizCategoryId: String,
    quizLevelId: String,
    navigateUp: () -> Unit,
    navigateToQuizResultScore: (points: Int) -> Unit,
    viewModel: QuizPlayViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.onEvent(QuizPlayEvent.FetchAllQuiz(quizCategoryId, quizLevelId))
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    if (state.isLoading) {
        LoadingDialog()
    } else {
        QuizPlayContent(
            position = state.position,
            timer = state.timer,
            quiz = state.quizItem,
            totalQuiz = state.totalQuiz,
            selectedChoice = state.selectedChoice,
            choices = state.choices,
            isCorrect = state.isCorrect,
            answerState = state.answerState,
            navigateUp = navigateUp,
            onClick = { choice ->
                viewModel.onEvent(QuizPlayEvent.OnAnswered(choice))
            }
        )
    }

    LaunchedEffect(state.isCorrect, state.answerState) {
        if (state.isCorrect) {
            delay(2.seconds)
            viewModel.onEvent(QuizPlayEvent.MoveToNext)
        }

        if (!state.isCorrect && state.answerState != Answer.NONE) {
            Toast.makeText(context, "Wrong", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(state.isAllQuizFinished) {
        if (state.isAllQuizFinished) {
            navigateUp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizPlayContent(
    position: Int,
    timer: Int,
    quiz: QuizItem,
    isCorrect: Boolean,
    choices: List<String>,
    totalQuiz: Int,
    selectedChoice: String,
    answerState: Answer,
    navigateUp: () -> Unit,
    onClick: (choice: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = navigateUp,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = null
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 8.dp, end = 16.dp)
                ) {
                    val progress by animateFloatAsState(
                        targetValue = position.toFloat() / totalQuiz.toFloat(),
                        animationSpec = tween(1000)
                    )
                    LinearProgressIndicator(
                        progress = progress,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "$position/$totalQuiz",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        },
        modifier = modifier
    ) { contentPadding ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(contentPadding)
                .selectableGroup()
        ) {
            item {
                QuestionSection(
                    timer = timer,
                    content = {
                        Text(
                            text = quiz.question,
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    },
                    modifier = Modifier
                        .padding(top = 32.dp, bottom = 16.dp)
                )
            }
            items(choices) { choice ->
                ItemQuizChoice(
                    answerState = if (choice == selectedChoice) answerState else Answer.NONE,
                    choice = choice,
                    selected = choice == selectedChoice,
                    onClick = {
                        if (answerState == Answer.NONE) onClick(choice)
                    }
                )
            }
        }
    }
}

@Composable
fun QuestionSection(
    timer: Int,
    content: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(200.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
                shape = CardDefaults.elevatedShape
            )
    ) {
        Box(Modifier.padding(16.dp).fillMaxSize()) {
            content()
            TimerIndicator(
                timer = timer,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-48).dp)
            )
        }
    }
}

@Composable
fun TimerIndicator(
    timer: Int,
    modifier: Modifier = Modifier,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = timer.toFloat() / 10f,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
                shape = CircleShape
            )
    ) {
        CircularProgressIndicator(
            progress = animatedProgress,
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.Center)
        )
        Text(
            text = "$timer",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}