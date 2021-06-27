package ge.c0d3in3.healthify.repository

import com.google.firebase.auth.FirebaseAuth
import ge.c0d3in3.healthify.firestore.FirestoreRepository
import ge.c0d3in3.healthify.model.Response
import ge.c0d3in3.healthify.model.StepData
import ge.c0d3in3.healthify.model.User
import ge.c0d3in3.healthify.presentation.auth.repository.AuthRepositoryImpl
import ge.c0d3in3.healthify.room.UserDao

class UserRepositoryImpl(
    private val firestore: FirestoreRepository,
    private val userDao: UserDao,
    private val firebaseAuth: FirebaseAuth
) :
    UserRepository {

    override fun isUserLoggedIn() = firebaseAuth.currentUser != null

    override fun getUserUid() = firebaseAuth.currentUser?.uid
        ?: throw IllegalStateException("user is not logged in!")

    override suspend fun getUserData(userId: String): Response<User> {
        return firestore.getDocument(AuthRepositoryImpl.USERS, userId, User::class.java)
    }

    override suspend fun createUser(user: User) {
        userDao.saveUser(user.copy(uid = getUserUid()))
    }

    override suspend fun saveUser(user: User, saveRemotely: Boolean) {
        userDao.saveUser(user)
        if (saveRemotely)
            writeUserInFirebase(user)
    }

    suspend fun saveUserRemotely(user: User) {
        writeUserInFirebase(user)
    }

    override suspend fun getUser(): User {
        return userDao.getUser(getUserUid())
    }

    override suspend fun updateSteps(steps: List<StepData>) {
        userDao.updateSteps(steps, getUserUid())
        saveUserRemotely(getUser())
    }

    override suspend fun writeUserInFirebase(user: User) {
        firestore.setDocument(AuthRepositoryImpl.USERS, user.uid, user)
    }
}