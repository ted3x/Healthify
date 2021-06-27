package ge.c0d3in3.healthify.presentation.onboarding.di

import ge.c0d3in3.healthify.presentation.onboarding.vm.OnboardingViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val onboardingModule = module {
    viewModel { OnboardingViewModel(app = androidApplication(), userRepository = get()) }
}