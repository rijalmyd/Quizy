package com.bangkit23.quizy.domain.model.user

data class User(
    val id: String = "",
    val name: String = "",
    val avatar: String? = null,
    val points: Int = 0,
    val ranking: Int = 0,
)
