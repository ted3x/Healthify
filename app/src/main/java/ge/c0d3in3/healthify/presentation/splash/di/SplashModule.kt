package ge.c0d3in3.healthify.presentation.splash.di

import ge.c0d3in3.healthify.presentation.splash.vm.SplashViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val splashModule = module {
    viewModel { SplashViewModel(app = androidApplication()) }
}