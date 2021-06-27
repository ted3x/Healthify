package ge.c0d3in3.healthify.presentation.dashboard

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ge.c0d3in3.healthify.base.BaseViewModel
import ge.c0d3in3.healthify.extensions.getToday
import ge.c0d3in3.healthify.model.StepData
import ge.c0d3in3.healthify.repository.StepCounterRepository
import kotlinx.coroutines.launch

class DashboardViewModel(
    app: Application,
    private val stepCounterRepository: StepCounterRepository
) :
    BaseViewModel(app) {

    var todaySteps :MutableLiveData<Int> = MutableLiveData(0)
    val stepData: MutableLiveData<StepData?> = MutableLiveData()
    var lastStep =
        app.applicationContext.getSharedPreferences("app", Context.MODE_PRIVATE).getInt("steps", 0)

    init {
        updateSteps()
    }

    private fun updateSteps() {
        viewModelScope.launch {
            val result = stepCounterRepository.getSteps(getToday())
            if(result.isNotEmpty())
                stepData.value = result.first()
            else
                stepData.value = StepData(getToday(), 0)
        }
    }

    fun onStepChanged(step: Float) {
        if(lastStep > step) lastStep = step.toInt()
        todaySteps.value = (stepData.value?.steps ?: 0) + (step - lastStep).toInt()
        if (stepData.value != null && stepData.value?.timestamp != getToday()) {
            saveUser()
        }
    }

    fun saveUser() {
        val steps = todaySteps.value!!
        viewModelScope.launch {
            val stepsData = stepCounterRepository.getAllSteps().toMutableList()
            if(stepsData.isEmpty()){
                stepData.value = StepData(getToday(), steps)
                stepsData.add(stepData.value!!)
            }
            else {
                if (stepsData.last().timestamp != getToday()) {
                    stepsData.last().steps = steps
                    stepCounterRepository.saveSteps(stepsData)
                    stepData.value = StepData(getToday(), 0)
                    stepsData.add(stepData.value!!)
                    lastStep += steps
                    todaySteps.value = 0
                } else
                    stepsData.last().steps = steps
            }
            stepCounterRepository.saveSteps(stepsData)
        }
    }
}