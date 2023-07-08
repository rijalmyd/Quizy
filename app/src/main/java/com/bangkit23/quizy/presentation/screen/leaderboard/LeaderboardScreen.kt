package com.bangkit23.quizy.presentation.screen.leaderboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bangkit23.quizy.domain.model.user.User
import com.bangkit23.quizy.presentation.components.ItemLeaderboard
import com.bangkit23.quizy.presentation.components.ItemTopUserLeader
import com.bangkit23.quizy.presentation.ui.theme.QuizyTheme

@Composable
fun LeaderboardScreen(
    navigateUp: () -> Unit,
    viewModel: LeaderboardViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LeaderboardContent(
        listLeaderboard = state.listLeaderboard,
        navigateUp = navigateUp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardContent(
    listLeaderboard: List<User>,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Leaderboard") },
                navigationIcon = {
                    IconButton(
                        onClick = navigateUp
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = "Navigate Back"
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier.padding(contentPadding)
        ) {
            item {
                TopUserLeaderboard(
                    firstPosition = listLeaderboard.firstOrNull { it.ranking == 1 } ?: User(),
                    secondPosition = listLeaderboard.firstOrNull { it.ranking == 2 } ?: User(),
                    thirdPosition = listLeaderboard.firstOrNull { it.ranking == 3 } ?: User(),
                )
                Spacer(Modifier.height(16.dp))
            }
            items(items = listLeaderboard.filter { it.ranking > 3 }, key = { it.id }) {
                ItemLeaderboard(
                    name = it.name,
                    photoUrl = it.avatar,
                    points = it.points,
                    position = it.ranking
                )
            }
        }
    }
}

@Composable
fun TopUserLeaderboard(
    firstPosition: User,
    secondPosition: User,
    thirdPosition: User,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(
        modifier = modifier.fillMaxWidth()
    ) {
        val (firstUser, secondUser, thirdUser) = createRefs()

        ItemTopUserLeader(
            rankingPosition = 1,
            image = firstPosition.avatar,
            name = firstPosition.name,
            points = firstPosition.points,
            modifier = Modifier
                .constrainAs(firstUser) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
        )
        ItemTopUserLeader(
            rankingPosition = 2,
            image = secondPosition.avatar,
            name = secondPosition.name,
            points = secondPosition.points,
            modifier = Modifier
                .constrainAs(secondUser) {
                    top.linkTo(firstUser.top, margin = 72.dp)
                    end.linkTo(firstUser.start)
                    start.linkTo(parent.start)
                }
        )
        ItemTopUserLeader(
            rankingPosition = 3,
            image = thirdPosition.avatar,
            name = thirdPosition.name,
            points = thirdPosition.points,
            modifier = Modifier
                .constrainAs(thirdUser) {
                    top.linkTo(firstUser.top, margin = 72.dp)
                    start.linkTo(firstUser.end)
                    end.linkTo(parent.end)
                }
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun LeaderboardContentPreview() {
    QuizyTheme {
        LeaderboardContent(
            listLeaderboard = emptyList(),
            navigateUp = {},
        )
    }
}