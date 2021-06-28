package ge.c0d3in3.healthify.presentation.profile.statistic

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ge.c0d3in3.healthify.R
import ge.c0d3in3.healthify.base.BaseViewModel
import ge.c0d3in3.healthify.model.StepData
import ge.c0d3in3.healthify.repository.UserRepository
import kotlinx.coroutines.launch
import java.lang.String.format
import java.text.DateFormat
import java.text.MessageFormat.format
import java.text.SimpleDateFormat
import java.util.*

class StatisticsViewModel(app: Application, private val userRepository: UserRepository) :
    BaseViewModel(app) {

    val date: LiveData<String> get() = _date
    private val _date: MutableLiveData<String> = MutableLiveData()
    val steps: LiveData<List<StatisticItem>> get() = _steps
    private val _steps: MutableLiveData<List<StatisticItem>> = MutableLiveData()

    init {
        viewModelScope.launch {
            val user = userRepository.getUser()
            if (user.steps.isEmpty()) return@launch
            val stepData = user.steps.last()
            _steps.postValue(parseItems(stepData))
            _date.postValue(getDate(stepData.timestamp))
        }
    }

    private fun parseItems(stepData: StepData): List<StatisticItem> {
        val list = arrayListOf<StatisticItem>()
        list.add(
            StatisticItem(
                R.drawable.ic_footsteps,
                "Steps",
                stepData.steps,
                stepData.targetStep
            )
        )
        list.add(
            StatisticItem(
                R.drawable.ic_water_glass,
                "Water",
                stepData.water,
                stepData.targetWater
            )
        )
        list.add(StatisticItem(R.drawable.ic_sleep, "Sleep", stepData.sleep, stepData.targetStep))
        return list
    }

    private fun getDate(timestamp: Long): String {
        return try {
            SimpleDateFormat("dd/MM/yyyy", Locale.US).format(timestamp)
        } catch (e: Exception) {
            ""
        }
    }
}