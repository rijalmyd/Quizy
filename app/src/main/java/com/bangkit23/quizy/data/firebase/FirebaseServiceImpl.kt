package com.bangkit23.quizy.data.firebase

import android.content.Intent
import com.bangkit23.quizy.domain.model.quiz.QuizCategory
import com.bangkit23.quizy.domain.model.quiz.QuizItem
import com.bangkit23.quizy.domain.model.quiz.QuizLevel
import com.bangkit23.quizy.domain.model.sign_in.SignInResult
import com.bangkit23.quizy.domain.model.user.User
import com.bangkit23.quizy.util.toUser
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseServiceImpl @Inject constructor(
    private val onTapClient: SignInClient,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : FirebaseService {

    override suspend fun signInGoogleWithIntent(intent: Intent): SignInResult {
        val credential = onTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleAuthCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
        val signInTask = auth.signInWithCredential(googleAuthCredential).await()
        val user = signInTask.user
        if (!isUserAlreadyExist(user.toUser())) {
            saveUser(user.toUser())
        }
        return SignInResult(
            user = user.toUser(),
            errorMessage = null
        )
    }

    override suspend fun signOut() {
        onTapClient.signOut().await()
        auth.signOut()
    }

    override suspend fun getSignedUser() = auth.currentUser?.toUser()

    override suspend fun getUserStatistic(): User? {
        return getLeaderboard()
            .find { it.id == getSignedUser()?.id }
    }

    override suspend fun addPoints(points: Int) {
        val currentPoints = getUserStatistic()?.points
        firestore.collection("users")
            .document(getSignedUser()?.id.toString())
            .update("points", currentPoints?.plus(points))
    }

    override suspend fun getLeaderboard(): List<User> {
        return firestore.collection("users")
            .orderBy("points", Query.Direction.DESCENDING)
            .get()
            .await()
            .toObjects(User::class.java)
            .mapIndexed { index, user -> user.copy(ranking = index + 1) }
    }

    override suspend fun getQuizCategories(): List<QuizCategory> {
        return firestore.collection("quiz")
            .orderBy("isNew", Query.Direction.DESCENDING)
            .get()
            .await()
            .toObjects(QuizCategory::class.java)
    }

    override suspend fun getQuizLevel(quizCategoryId: String): List<QuizLevel> {
        return firestore.collection("quiz")
            .document(quizCategoryId)
            .collection("level")
            .orderBy("pointsEarned", Query.Direction.ASCENDING)
            .get()
            .await()
            .toObjects(QuizLevel::class.java)
    }

    override suspend fun getQuizQuestions(quizCategoryId: String, quizLevelId: String): List<QuizItem> {
        return firestore.collection("quiz")
            .document(quizCategoryId)
            .collection("level")
            .document(quizLevelId)
            .collection("question")
            .get()
            .await()
            .toObjects(QuizItem::class.java)
    }

    private suspend fun isUserAlreadyExist(user: User): Boolean {
        return firestore.collection("users")
            .document(user.id)
            .get()
            .await()
            .exists()
    }

    private suspend fun saveUser(user: User) {
        firestore.collection("users")
            .document(user.id)
            .set(user)
            .await()
    }
}