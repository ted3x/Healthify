package ge.c0d3in3.healthify.presentation.auth.repository

import com.google.firebase.auth.AuthResult
import ge.c0d3in3.healthify.model.Response
import ge.c0d3in3.healthify.model.User

interface AuthRepository {
    suspend fun getUser(userId: String): Response<User>
    suspend fun saveUser(user: User)
    suspend fun signIn(email: String, password: String): Response<AuthResult>
    suspend fun signUp(email: String, password: String): Response<AuthResult>
    suspend fun writeUserInFirebase(user: User)
}