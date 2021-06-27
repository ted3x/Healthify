package ge.c0d3in3.healthify.app

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ge.c0d3in3.healthify.firestore.FirestoreRepository
import ge.c0d3in3.healthify.firestore.FirestoreRepositoryImpl
import ge.c0d3in3.healthify.repository.UserRepository
import ge.c0d3in3.healthify.repository.UserRepositoryImpl
import ge.c0d3in3.healthify.repository.StepCounterRepository
import ge.c0d3in3.healthify.room.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    fun provideDatabase(context: Context) = AppDatabase.getInstance(context)
    fun provideUserDao(database: AppDatabase) = database.userDao()
    single { provideDatabase(context = androidContext()) }
    single { provideUserDao(database = get()) }
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single<FirestoreRepository> { FirestoreRepositoryImpl(firestore = get()) }
    single<UserRepository> {
        UserRepositoryImpl(
            firestore = get(),
            userDao = get(),
            firebaseAuth = get()
        )
    }
    single { StepCounterRepository(userRepository = get()) }
}