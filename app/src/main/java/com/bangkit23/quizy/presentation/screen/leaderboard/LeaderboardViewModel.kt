package com.bangkit23.quizy.presentation.screen.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit23.quizy.domain.model.user.User
import com.bangkit23.quizy.domain.repository.UserRepository
import com.bangkit23.quizy.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LeaderboardState())
    val state = _state.asStateFlow()

    init {
        getLeaderboard()
    }

    private fun getLeaderboard() = viewModelScope.launch {
        userRepository.getLeaderboard().collect { result ->
            when (result) {
                is Result.Error -> _state.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = result.message
                    )
                }
                is Result.Loading -> _state.update {
                    it.copy(
                        isLoading = true
                    )
                }
                is Result.Success -> _state.update {
                    it.copy(
                        isLoading = false,
                        listLeaderboard = result.data
                    )
                }
            }
        }
        delay(1000)
    }

    private val dummyLeaderboard = listOf(
        User(
            id = "01",
            name = "Rijal Muhyidin",
            avatar = "https://avatars.githubusercontent.com/u/71188953?v=4",
            points = 3200,
            ranking = 1,
        ),
        User(
            id = "02",
            name = "Anggi",
            avatar = "https://assets.jatimnetwork.com/crop/0x0:0x0/750x500/webp/photo/2021/11/21/2071322425.jpg",
            points = 2030,
            ranking = 2,
        ),
        User(
            id = "03",
            name = "Marcelia",
            avatar = "https://cdn-icons-png.flaticon.com/128/4140/4140039.png",
            points = 1330,
            ranking = 3,
        ),
        User(
            id = "04",
            name = "Galih Wicaksono",
            avatar = "https://cdn-icons-png.flaticon.com/128/4139/4139951.png",
            points = 1203,
            ranking = 4,
        ),
        User(
            id = "05",
            name = "Nur Aisyah",
            avatar = "https://cdn-icons-png.flaticon.com/128/4140/4140040.png",
            points = 1020,
            ranking = 5,
        ),
        User(
            id = "06",
            name = "Firmansyah",
            avatar = "https://cdn-icons-png.flaticon.com/128/1999/1999625.png",
            points = 920,
            ranking = 6,
        ),
        User(
            id = "07",
            name = "Bima Putra",
            avatar = "https://cdn-icons-png.flaticon.com/128/4139/4139981.png",
            points = 746,
            ranking = 7,
        ),
        User(
            id = "08",
            name = "Yani",
            avatar = "https://cdn-icons-png.flaticon.com/128/4140/4140047.png",
            points = 340,
            ranking = 8,
        ),
    )
}