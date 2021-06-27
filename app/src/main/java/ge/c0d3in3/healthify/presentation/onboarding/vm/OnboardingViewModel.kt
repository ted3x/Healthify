package ge.c0d3in3.healthify.presentation.onboarding.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ge.c0d3in3.healthify.base.BaseViewModel
import ge.c0d3in3.healthify.repository.UserRepository
import ge.c0d3in3.healthify.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OnboardingViewModel(app: Application, private val userRepository: UserRepository) :
    BaseViewModel(app) {

    private val user = User(steps = mutableListOf())
    val nextButtonVisibility: LiveData<Boolean> get() = _nextButtonVisibility
    private val _nextButtonVisibility = MutableLiveData<Boolean>()

    val navigateToDashboard: LiveData<Boolean> get() = _navigateToDashboard
    private val _navigateToDashboard = MutableLiveData<Boolean>()

    fun setNextButtonVisibility(visible: Boolean) {
        if(_nextButtonVisibility.value == visible) return
        _nextButtonVisibility.value = visible
    }

    fun setUserFirstName(firstName: String){
        user.firstName = firstName
    }

    fun setUserLastName(lastName: String){
        user.lastName = lastName
    }

    fun setUserAge(age: Int){
        user.age = age
    }

    fun setUserHeight(height: Int){
        user.height = height
    }

    fun getUserHeight() = user.height

    fun setUserWeight(weight: Double){
        user.weight = weight
    }

    fun getUserWeight() = user.weight

    fun setUserTargetWeight(targetWeight: Double){
        user.targetWeight = targetWeight
    }

    fun setUserProfilePicture(profilePictureUrl: String){
        user.profilePicture = profilePictureUrl
        user.uid = userRepository.getUserUid()
        viewModelScope.launch(Dispatchers.Default) {
            userRepository.saveUser(user, true)
            _navigateToDashboard.postValue(true)
        }
    }
}