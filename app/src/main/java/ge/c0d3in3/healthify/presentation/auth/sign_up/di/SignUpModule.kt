package ge.c0d3in3.healthify.presentation.auth.sign_up.di

import ge.c0d3in3.healthify.presentation.auth.repository.AuthRepository
import ge.c0d3in3.healthify.presentation.auth.repository.AuthRepositoryImpl
import ge.c0d3in3.healthify.presentation.auth.sign_up.vm.SignUpViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val signUpModule = module {
    single<AuthRepository>(override = true) { AuthRepositoryImpl(firebaseAuth = get()) }
    viewModel { SignUpViewModel(app = androidApplication(), authRepository = get()) }
}