package com.bangkit23.quizy.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bangkit23.quizy.presentation.screen.quiz_play.Answer

@Composable
fun ItemQuizChoice(
    answerState: Answer,
    selected: Boolean,
    choice: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        colors = when (answerState) {
            Answer.NONE -> CardDefaults.outlinedCardColors()
            Answer.CORRECT -> CardDefaults.outlinedCardColors(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                    1.dp
                )
            )
            Answer.WRONG -> CardDefaults.outlinedCardColors(
                containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.1f),
            )
        },
        border = when (answerState) {
            Answer.NONE -> CardDefaults.outlinedCardBorder()
            Answer.CORRECT -> CardDefaults.outlinedCardBorder().copy(
                brush = Brush.linearGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primary
                    )
                )
            )
            Answer.WRONG -> CardDefaults.outlinedCardBorder().copy(
                brush = Brush.linearGradient(
                    listOf(
                        MaterialTheme.colorScheme.error,
                        MaterialTheme.colorScheme.error
                    )
                )
            )
        },
        modifier = modifier.selectable(
            selected = selected,
            onClick = onClick
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp)
        ) {
            Text(
                text = choice,
                style = when (answerState) {
                    Answer.NONE -> MaterialTheme.typography.titleMedium
                    Answer.CORRECT -> MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                    Answer.WRONG -> MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.error
                    )
                },
                modifier = Modifier
                    .weight(1f)
            )
            when (answerState) {
                Answer.NONE -> {}
                Answer.CORRECT -> {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            )
                            .padding(2.dp)
                    )
                }
                Answer.WRONG -> {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.error.copy(alpha = 0.8f),
                                shape = CircleShape
                            )
                            .padding(2.dp)
                    )
                }
            }
        }
    }
}