package ge.c0d3in3.healthify.presentation.splash.vm

import android.app.Application
import androidx.lifecycle.viewModelScope
import ge.c0d3in3.healthify.R
import ge.c0d3in3.healthify.base.BaseViewModel
import ge.c0d3in3.healthify.extensions.execute
import ge.c0d3in3.healthify.repository.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(app: Application, private val userRepository: UserRepository) :
    BaseViewModel(app) {

    init {
        viewModelScope.launch {
            if (userRepository.isUserLoggedIn()) {
                userRepository.getUserData(userRepository.getUserUid()).execute(
                    onSuccess = {
                        userRepository.saveUser(it)
                        navigateTo(R.id.action_splashFragment_to_dashboardFragment)
                    },
                    onFail = {
                        navigateTo(R.id.action_splashFragment_to_onboardingFragment)
                    }
                )
            } else {
                delay(SPLASH_DELAY)
                navigateTo(R.id.action_splashFragment_to_signInFragment)
            }
        }
    }

    companion object {
        private const val SPLASH_DELAY = 1500L
    }
}