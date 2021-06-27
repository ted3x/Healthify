package ge.c0d3in3.healthify.presentation.onboarding.screens.target_weight

import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val onboardingTargetWeightModule = module {
    viewModel { OnboardingTargetWeightViewModel(app = androidApplication()) }
}