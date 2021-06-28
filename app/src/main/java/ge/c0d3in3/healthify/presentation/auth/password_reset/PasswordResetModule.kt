package ge.c0d3in3.healthify.presentation.auth.password_reset

import ge.c0d3in3.healthify.presentation.auth.repository.AuthRepository
import ge.c0d3in3.healthify.presentation.auth.repository.AuthRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val passwordResetModule = module {
    single<AuthRepository>(override = true) { AuthRepositoryImpl(firebaseAuth = get()) }
    viewModel { PasswordResetViewModel(app = androidApplication(), authRepository = get()) }
}