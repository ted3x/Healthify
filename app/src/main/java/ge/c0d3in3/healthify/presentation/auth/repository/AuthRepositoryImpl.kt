package ge.c0d3in3.healthify.presentation.auth.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import ge.c0d3in3.healthify.extensions.execute
import ge.c0d3in3.healthify.firestore.FirestoreRepositoryImpl
import ge.c0d3in3.healthify.model.Response
import ge.c0d3in3.healthify.model.User
import ge.c0d3in3.healthify.room.UserDao

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirestoreRepositoryImpl,
    private val userDao: UserDao
) :
    AuthRepository {
    override suspend fun getUser(userId: String): Response<User> {
        return firestore.getDocument(USERS, userId)
    }

    override suspend fun saveUser(user: User) {
        userDao.saveUser(user)
    }

    override suspend fun signIn(email: String, password: String): Response<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, password).execute()
    }

    override suspend fun signUp(email: String, password: String): Response<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(email, password).execute()
    }

    override suspend fun writeUserInFirebase(user: User) {
        firestore.setDocument(USERS, user.uid, user)
    }

    companion object {
        private const val USERS = "users"
    }
}