package ge.c0d3in3.healthify.presentation.dashboard

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ge.c0d3in3.healthify.R
import ge.c0d3in3.healthify.base.BaseViewModel
import ge.c0d3in3.healthify.extensions.getToday
import ge.c0d3in3.healthify.model.StepData
import ge.c0d3in3.healthify.model.User
import ge.c0d3in3.healthify.repository.StepCounterRepository
import ge.c0d3in3.healthify.repository.UserRepository
import kotlinx.coroutines.launch

class DashboardViewModel(
    app: Application,
    private val userRepository: UserRepository,
    private val stepCounterRepository: StepCounterRepository
) :
    BaseViewModel(app) {

    private var savedSteps = 0
    private var isSaving = false
    var isCompleted = false
    private var firstBoot = true
    val user: LiveData<User> get() = _user
    private val _user = MutableLiveData<User>()
    val stepData: MutableLiveData<StepData> = MutableLiveData()
    var todaySteps: MutableLiveData<Int> = MutableLiveData(0)
    var lastStep =
        app.applicationContext.getSharedPreferences("app", Context.MODE_PRIVATE).getInt("steps", 0)

    init {
        viewModelScope.launch {
            _user.postValue(userRepository.getUser())
            updateSteps()
        }
    }

    private suspend fun updateSteps() {
        val result = stepCounterRepository.getSteps(getToday())
        if (result.isNotEmpty()) {
            val todayStep = result.first()
            stepData.value = todayStep
            savedSteps = todayStep.steps
            todaySteps.value = savedSteps
            isCompleted = todayStep.steps >= todayStep.targetStep
        } else {
            isCompleted = false
            stepData.value = getDefaultStepData()
        }
    }

    fun onStepChanged(step: Float) {
        if (lastStep > step) lastStep = step.toInt()
        todaySteps.value = (step - lastStep).toInt() + savedSteps
        if (!isSaving && (isCompleted || (stepData.value != null && stepData.value?.timestamp != getToday()))) {
            isSaving = true
            saveSteps()
        }
    }

    fun saveSteps() {
        if (firstBoot) {
            firstBoot = false
            return
        }
        val steps = todaySteps.value!!
        viewModelScope.launch {
            if (stepData.value?.timestamp != getToday()) {
                stepCounterRepository.updateSteps(stepData.value!!)
                user.value?.weight = user.value!!.weight + stepData.value!!.weightGain
                stepData.value = getDefaultStepData()
                savedSteps = 0
                todaySteps.value = 0
            } else {
                stepData.value?.steps = steps
                stepCounterRepository.updateSteps(stepData.value!!)
            }
            isSaving = false
            isCompleted = false
            app.applicationContext.getSharedPreferences("app", Context.MODE_PRIVATE).edit()
                .putLong("lastSaveTime", System.currentTimeMillis()).apply()
        }
    }

    fun addSleep(sleepDuration: Int) {
        val sleep = stepData.value!!.sleep + sleepDuration
        stepData.value = stepData.value!!.copy(sleep = sleep)
    }

    fun drinkWater() {
        val water = stepData.value!!.water + 1
        stepData.value = stepData.value!!.copy(water = water)
    }

    fun getDefaultStepData(): StepData {
        val user = user.value!!
        return StepData(
            getToday(),
            targetWater = user.targetWater,
            targetStep = user.targetStep,
            targetSleep = user.targetSleep
        )
    }

    fun addWeight(gainedWeight: Double) {
        val weight = stepData.value!!.weightGain + gainedWeight
        stepData.value = stepData.value!!.copy(weightGain = weight)
        _user.value = user.value!!.copy(weight = _user.value!!.weight + gainedWeight)
        viewModelScope.launch {
            userRepository.updateWeight(_user.value!!.weight + gainedWeight)
        }
    }

    fun navigateToProfile() {
        viewModelScope.launch {
            saveSteps()
            navigateTo(R.id.action_dashboardFragment_to_profileFragment)
        }
    }
}