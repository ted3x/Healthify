package ge.c0d3in3.healthify.repository

import ge.c0d3in3.healthify.model.Response
import ge.c0d3in3.healthify.model.StepData
import ge.c0d3in3.healthify.model.User

interface UserRepository {
    fun isUserLoggedIn(): Boolean
    fun getUserUid(): String
    suspend fun updateSteps(steps: List<StepData>)
    suspend fun getUser(): User
    suspend fun getUserData(userId: String): Response<User>
    suspend fun createUser(user: User)
    suspend fun saveUser(user: User, saveRemotely: Boolean = false)
    suspend fun writeUserInFirebase(user: User)
}