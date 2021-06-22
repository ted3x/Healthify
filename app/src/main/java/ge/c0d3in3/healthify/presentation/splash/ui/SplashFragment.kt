package ge.c0d3in3.healthify.presentation.splash.ui

import android.os.Bundle
import android.view.View
import ge.c0d3in3.healthify.R
import ge.c0d3in3.healthify.base.BaseFragment
import ge.c0d3in3.healthify.databinding.FragmentSplashBinding
import ge.c0d3in3.healthify.presentation.splash.di.splashModule
import ge.c0d3in3.healthify.presentation.splash.vm.SplashViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SplashFragment :
    BaseFragment<FragmentSplashBinding, SplashViewModel>(FragmentSplashBinding::inflate) {

    override var module = splashModule
    override val vm by viewModel<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.checkNetwork()
        vm.isNetworkAvailable.observe { available ->
            if (available)
                true
            else
                showNetworkNotAvailableDialog()
        }
    }

    private fun showNetworkNotAvailableDialog() {
        with(getDialog()) {
            setDialogTitle(R.string.splash_network_is_not_available_title)
            setDialogMessage(R.string.splash_network_is_not_available_message)
            setOnPositiveClickListener { requireActivity().finish() }
            setPositiveButtonText(R.string.component_close)
            setCancelable(false)
            show()
        }
    }
}