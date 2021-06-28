package ge.c0d3in3.healthify.presentation.profile.statistic.di

import ge.c0d3in3.healthify.presentation.profile.statistic.StatisticsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val statisticsModule = module {
    viewModel { StatisticsViewModel(app = androidApplication(), userRepository = get()) }
}