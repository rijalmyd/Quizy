package com.bangkit23.quizy.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.bangkit23.quizy.domain.model.quiz.QuizCategory
import com.bangkit23.quizy.domain.model.user.User
import com.bangkit23.quizy.presentation.components.CardUserStatistic
import com.bangkit23.quizy.presentation.components.ItemQuiz
import com.bangkit23.quizy.presentation.screen.quiz_level.QuizLevelScreen
import com.bangkit23.quizy.presentation.ui.theme.QuizyTheme

@Composable
fun HomeScreen(
    navigateToLeaderboard: () -> Unit,
    navigateToQuizPlay: (quizCategoryId: String, quizLevelId: String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeContent(
        user = state.user,
        listQuiz = state.listQuiz,
        navigateToLeaderboard = navigateToLeaderboard,
        showDialogLevel = { quizCategoryId ->
            viewModel.onEvent(
                HomeEvent.OnDialogLevelShowHide(
                    isShown = true,
                    quizCategoryId = quizCategoryId
                )
            )
        }
    )

    if (state.isDialogLevelShown) {
        state.quizCategoryId?.let { id ->
            QuizLevelScreen(
                quizCategoryId = id,
                onDismiss = {
                    viewModel.onEvent(HomeEvent.OnDialogLevelShowHide(isShown = false))
                },
                navigateToQuizPlay = { quizCategoryId, quizLevelId ->
                    viewModel.onEvent(HomeEvent.OnDialogLevelShowHide(isShown = false))
                    navigateToQuizPlay(quizCategoryId, quizLevelId)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    user: User?,
    listQuiz: List<QuizCategory>,
    navigateToLeaderboard: () -> Unit,
    showDialogLevel: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            Column {
                Spacer(Modifier.height(16.dp))
                TopBarProfile(
                    name = user?.name.toString(),
                    imageProfile = user?.avatar,
                    onProfileClick = {},
                )
            }
        },
    ) { contentPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier.padding(contentPadding)
        ) {
            item(
                span = {
                    GridItemSpan(2)
                }
            ) {
                Column(Modifier.fillMaxWidth()) {
                    CardUserStatistic(
                        ranking = user?.ranking ?: 0,
                        points = user?.points ?: 0,
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 24.dp)
                            .clickable { navigateToLeaderboard() }
                    )
                    Text(
                        text = "Let's play",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                    )
                }
            }
            items(items = listQuiz, key = { it.id }) {
                ItemQuiz(
                    image = it.image,
                    title = it.title,
                    totalQuestions = it.totalQuestions,
                    onQuizItemClick = {
                        showDialogLevel(it.id)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarProfile(
    name: String,
    imageProfile: String?,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Column {
                Text(
                    text = "Hi, ${name.split(" ").getOrNull(0)}",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "Let's make your day incredible!",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        },
        actions = {
            IconButton(
                onClick = onProfileClick,
            ) {
                if (imageProfile != null) {
                    AsyncImage(
                        model = imageProfile,
                        contentDescription = null,
                        contentScale = ContentScale.Inside,
                        modifier = Modifier.fillMaxSize()
                            .clip(CircleShape)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                shape = CircleShape
                            )
                    ) {
                        Text(
                            text = "${name.firstOrNull()}",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
            Spacer(Modifier.width(8.dp))
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarProfilePreview() {
    QuizyTheme {
        TopBarProfile(
            name = "Rijal",
            imageProfile = null,
            onProfileClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    QuizyTheme {

    }
}