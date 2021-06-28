package ge.c0d3in3.healthify.presentation.profile

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ge.c0d3in3.healthify.R
import ge.c0d3in3.healthify.base.BaseViewModel
import ge.c0d3in3.healthify.presentation.auth.repository.AuthRepository
import ge.c0d3in3.healthify.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    app: Application,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : BaseViewModel(app) {

    val profilePicture: LiveData<String> get() = _profilePicture
    private val _profilePicture: MutableLiveData<String> = MutableLiveData()
    init {
        viewModelScope.launch {
            _profilePicture.postValue(userRepository.getUser().profilePicture)
        }
    }
    fun changeProfilePicture(url: String) {
        viewModelScope.launch {
            userRepository.updateUserPicture(url)
        }
    }

    fun logOut() {
        viewModelScope.launch(Dispatchers.Default) {
            authRepository.logOut()
            navigateTo(R.id.action_profileFragment_to_signInFragment)
        }
    }
}