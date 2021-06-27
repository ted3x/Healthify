package ge.c0d3in3.healthify.presentation.onboarding.screens.target_weight

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ge.c0d3in3.healthify.R
import ge.c0d3in3.healthify.base.BaseViewModel
import ge.c0d3in3.healthify.extensions.formatToWeight

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

    fun calculateBMI(weight: Double, height: Int): WeightInfo {
        val heightInMetres = height.toDouble() / 100
        val bmi = weight / (heightInMetres * heightInMetres)
        return getBMIInfo(bmi)
    }

    private fun getBMIInfo(bmi: Double): WeightInfo {
        val bmiInfo = when {
            bmi < 18.5 -> WeightInfo(
                text = app.getString(R.string.components_weight_underweight),
                textColorRes = R.color.primary
            )
            bmi in 18.5..25.9 -> WeightInfo(
                text = app.getString(R.string.components_weight_normal),
                textColorRes = R.color.green
            )
            bmi in 25.0..25.9 -> WeightInfo(
                text = app.getString(R.string.components_weight_overweight),
                textColorRes = R.color.yellow
            )

            bmi in 30.0..34.9 -> WeightInfo(
                text = app.getString(R.string.components_weight_obese),
                textColorRes = R.color.orange
            )
            else -> WeightInfo(
                text = app.getString(R.string.components_weight_extremely_obese),
                textColorRes = R.color.red
            )
        }
        return bmiInfo.copy(bmi = bmi)
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
                R.color.green
            )
        }
    }
}