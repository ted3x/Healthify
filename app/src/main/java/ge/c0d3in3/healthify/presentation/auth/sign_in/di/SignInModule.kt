package ge.c0d3in3.healthify.presentation.auth.sign_in.di

import ge.c0d3in3.healthify.presentation.auth.sign_in.vm.SignInViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val signInModule = module {
    viewModel { SignInViewModel(app = androidApplication(), authRepository = get()) }
}