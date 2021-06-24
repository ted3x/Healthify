package ge.c0d3in3.healthify.presentation.splash.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ge.c0d3in3.healthify.base.App
import ge.c0d3in3.healthify.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(app: Application) : BaseViewModel(app) {

    val isNetworkAvailable: LiveData<Boolean> get() = _isNetworkAvailable
    private val _isNetworkAvailable by lazy {
        MutableLiveData<Boolean>()
    }

    fun checkNetwork() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(SPLASH_DELAY)
            _isNetworkAvailable.postValue(isNetworkAvailable())
        }
    }

    private fun isNetworkAvailable() =
        Runtime.getRuntime().exec("ping -c 1 google.com").waitFor() == 0

    companion object {
        private const val SPLASH_DELAY = 1500L
    }
}