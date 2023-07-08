package com.bangkit23.quizy.presentation.screen.quiz_level

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.bangkit23.quizy.R
import com.bangkit23.quizy.domain.model.quiz.QuizLevel

@Composable
fun QuizLevelScreen(
    quizCategoryId: String,
    onDismiss: () -> Unit,
    navigateToQuizPlay: (quizCategoryId: String, quizLevelId: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: QuizLevelViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.onEvent(QuizLevelEvent.OnGetQuizLevel(quizCategoryId))
    }
    val state by viewModel.state.collectAsStateWithLifecycle()

    Dialog(
        onDismissRequest = {
            onDismiss()
            viewModel.onEvent(QuizLevelEvent.ResetState)
        },
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
                    shape = MaterialTheme.shapes.large
                )
                .defaultMinSize(minHeight = 384.dp)
        ) {
            ListLevel(
                quizCategoryId = quizCategoryId,
                quizLevel = state.quizLevel,
                navigateToQuizPlay = { quizCategoryId, quizLevelId ->
                    navigateToQuizPlay(quizCategoryId, quizLevelId)
                    viewModel.onEvent(QuizLevelEvent.ResetState)
                }
            )
        }
    }
}

@Composable
fun ListLevel(
    quizCategoryId: String,
    quizLevel: SnapshotStateList<QuizLevel>,
    navigateToQuizPlay: (quizCategoryId: String, quizLevelId: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        item {
            Text(
                text = "Pilih level",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        items(items = quizLevel, key = { it.id }) {
            ItemQuizLevel(
                level = it.name,
                icon = it.icon,
                pointsEarned = it.pointsEarned,
                onItemClick = {
                    navigateToQuizPlay(quizCategoryId, it.id)
                }
            )
        }
    }
}

@Composable
fun ItemQuizLevel(
    level: String,
    icon: String,
    pointsEarned: Int,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        modifier = modifier
            .clickable { onItemClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = icon,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = level,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "$pointsEarned",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(Modifier.width(8.dp))
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_coin),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}