package ge.c0d3in3.healthify.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class BaseViewModel(val app: Application) : AndroidViewModel(app) {

    val alertMessage: LiveData<Pair<String, String>> get() = _alertMessage
    protected val _alertMessage = MutableLiveData<Pair<String, String>>()

    val navigationAction: LiveData<Int> get() = _navigationAction
    private val _navigationAction = MutableLiveData<Int>()

    protected fun navigateTo(action: Int) {
        _navigationAction.postValue(action)
    }

    protected fun showAlert(titleRes: Int, messageRes: Int) {
        _alertMessage.postValue(
            Pair(
                app.applicationContext.getString(titleRes),
                app.applicationContext.getString(messageRes)
            )
        )
    }

    protected fun showAlert(titleRes: Int, message: String) {
        _alertMessage.postValue(Pair(app.applicationContext.getString(titleRes), message))
    }
}