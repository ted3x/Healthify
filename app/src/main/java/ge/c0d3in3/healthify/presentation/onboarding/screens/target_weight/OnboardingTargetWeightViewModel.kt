package ge.c0d3in3.healthify.presentation.onboarding.screens.target_weight

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ge.c0d3in3.components.formatToWeight
import ge.c0d3in3.healthify.R
import ge.c0d3in3.healthify.base.BaseViewModel

class OnboardingTargetWeightViewModel(app: Application) : BaseViewModel(app) {
    val mainWeight = IntRange(30, 180).toList()
    val secondaryWeight = IntRange(0, 9).toList()

    private var userWeight: Double = 0.0
    private var selectedMainWeight = mainWeight[0]
    private var selectedSecondaryWeight = secondaryWeight[0]

    val weightInfo: LiveData<WeightInfo> get() = _weightInfo
    private val _weightInfo = MutableLiveData<WeightInfo>()

    val selectedWeight: LiveData<Double> get() = _selectedWeight
    private val _selectedWeight =
        MutableLiveData<Double>(selectedMainWeight + selectedSecondaryWeight.toDouble())

    fun updateSelectedMainWeight(position: Int) {
        selectedMainWeight = mainWeight[position]
        updateSelectedWeight()
    }

    fun updateSelectedSecondaryWeight(position: Int) {
        selectedSecondaryWeight = secondaryWeight[position]
        updateSelectedWeight()
    }

    private fun updateSelectedWeight() {
        _selectedWeight.value = selectedMainWeight + selectedSecondaryWeight.toDouble() / 10
        updateWeightInfo()
    }

    fun setUserWeight(userWeight: Double) {
        this.userWeight = userWeight
    }

    fun updateWeightInfo() {
        val mSelectedWeight = this.selectedWeight.value!!
        _weightInfo.value = when {
            mSelectedWeight > userWeight -> WeightInfo(
                text = "Gain ${(mSelectedWeight - userWeight).formatToWeight()} Kg", textColorRes =
                R.color.green
            )
            mSelectedWeight == userWeight -> WeightInfo(
                text = "Maintain",
                textColorRes = R.color.primary
            )
            else -> WeightInfo(
                text = "Lose ${(userWeight - mSelectedWeight).formatToWeight()} Kg", textColorRes =
                R.color.red
            )
        }
    }
}