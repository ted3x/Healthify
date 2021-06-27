package ge.c0d3in3.healthify.presentation.auth.repository

import com.google.firebase.auth.AuthResult
import ge.c0d3in3.healthify.model.Response

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Response<AuthResult>
    suspend fun signUp(email: String, password: String): Response<AuthResult>
}