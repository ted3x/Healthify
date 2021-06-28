package ge.c0d3in3.healthify.presentation.profile.di

import ge.c0d3in3.healthify.presentation.auth.repository.AuthRepository
import ge.c0d3in3.healthify.presentation.auth.repository.AuthRepositoryImpl
import ge.c0d3in3.healthify.presentation.profile.ProfileViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {
    single<AuthRepository>(override = true) { AuthRepositoryImpl(firebaseAuth = get()) }
    viewModel { ProfileViewModel(app = androidApplication(), authRepository = get(), userRepository = get()) }
}