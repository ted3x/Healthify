package ge.c0d3in3.healthify.base

import android.app.Application
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ge.c0d3in3.healthify.firestore.FirestoreRepositoryImpl
import ge.c0d3in3.healthify.presentation.auth.repository.AuthRepository
import ge.c0d3in3.healthify.presentation.auth.repository.AuthRepositoryImpl
import ge.c0d3in3.healthify.room.AppDatabase
import org.koin.dsl.module

val appModule = module {
    fun provideDatabase(application: Application) = Room.databaseBuilder(
        application, AppDatabase::class.java,
        "healthify-db"
    ).fallbackToDestructiveMigration().build()

    fun provideUserDao(database: AppDatabase) = database.userDao()
    single { provideDatabase(application = get()) }
    single { provideUserDao(database = get()) }
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FirestoreRepositoryImpl(firestore = get()) }
    single<AuthRepository> {
        AuthRepositoryImpl(
            firebaseAuth = get(),
            firestore = get(),
            userDao = get()
        )
    }
}