package ge.c0d3in3.healthify.presentation.auth.change_password.di

import ge.c0d3in3.healthify.presentation.auth.change_password.ChangePasswordViewModel
import ge.c0d3in3.healthify.presentation.auth.repository.AuthRepository
import ge.c0d3in3.healthify.presentation.auth.repository.AuthRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val changePasswordModule = module {
    single<AuthRepository>(override = true) { AuthRepositoryImpl(firebaseAuth = get()) }
    viewModel { ChangePasswordViewModel(app = androidApplication(), authRepository = get()) }
}