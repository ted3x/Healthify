package ge.c0d3in3.healthify.presentation.auth.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import ge.c0d3in3.healthify.extensions.execute
import ge.c0d3in3.healthify.model.Response

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun signIn(email: String, password: String): Response<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, password).execute()
    }

    override suspend fun signUp(email: String, password: String): Response<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(email, password).execute()
    }

    companion object {
        const val USERS = "users"
    }
}