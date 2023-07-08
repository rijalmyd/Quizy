package com.bangkit23.quizy.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bangkit23.quizy.presentation.ui.theme.QuizyTheme

@Composable
fun ItemQuiz(
    image: String,
    title: String,
    totalQuestions: Int,
    onQuizItemClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier = modifier
            .clickable { onQuizItemClick() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = image,
                contentDescription = title,
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .size(80.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemQuizPreview() {
    QuizyTheme {
        ItemQuiz(
            image = "https://w7.pngwing.com/pngs/466/907/png-transparent-mit-department-of-mathematics-computer-icons-mathematical-notation-mathematical-problem-mathematics-blue-game-logo.png",
            title = "Math",
            totalQuestions = 20,
            onQuizItemClick = {},
        )
    }
}