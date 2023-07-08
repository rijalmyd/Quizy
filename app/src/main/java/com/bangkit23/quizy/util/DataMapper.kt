package com.bangkit23.quizy.util

import com.bangkit23.quizy.domain.model.user.User
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser?.toUser() = User(
    id = "${this?.uid}",
    name = "${this?.displayName}",
    avatar = "${this?.photoUrl}",
)