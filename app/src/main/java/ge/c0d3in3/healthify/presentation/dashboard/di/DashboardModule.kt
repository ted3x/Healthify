package ge.c0d3in3.healthify.presentation.dashboard.di

import ge.c0d3in3.healthify.presentation.dashboard.DashboardViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dashboardModule = module {
    viewModel {
        DashboardViewModel(
            app = androidApplication(),
            userRepository = get(),
            stepCounterRepository = get()
        )
    }
}