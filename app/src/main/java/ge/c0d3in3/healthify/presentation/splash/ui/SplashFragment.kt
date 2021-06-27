package ge.c0d3in3.healthify.presentation.splash.ui

import ge.c0d3in3.healthify.base.BaseFragment
import ge.c0d3in3.healthify.databinding.SplashFragmentBinding
import ge.c0d3in3.healthify.presentation.splash.di.splashModule
import ge.c0d3in3.healthify.presentation.splash.vm.SplashViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SplashFragment :
    BaseFragment<SplashFragmentBinding, SplashViewModel>(SplashFragmentBinding::inflate) {

    override val module = splashModule
    override val vm by viewModel<SplashViewModel>()

}