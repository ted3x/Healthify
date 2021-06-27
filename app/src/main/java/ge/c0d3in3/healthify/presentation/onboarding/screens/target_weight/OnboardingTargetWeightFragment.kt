package ge.c0d3in3.healthify.presentation.onboarding.screens.target_weight

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import ge.c0d3in3.healthify.R
import ge.c0d3in3.healthify.databinding.OnboardingTargetWeightFragmentBinding
import ge.c0d3in3.healthify.extensions.color
import ge.c0d3in3.healthify.extensions.formatToWeight
import ge.c0d3in3.healthify.extensions.span
import ge.c0d3in3.healthify.presentation.onboarding.screens.BaseOnboardingFragment
import org.koin.android.viewmodel.ext.android.viewModel

class OnboardingTargetWeightFragment :
    BaseOnboardingFragment<OnboardingTargetWeightFragmentBinding>(
        OnboardingTargetWeightFragmentBinding::inflate
    ) {
    override val onboardingTitleRes = R.string.onboarding_target_weight_title
    override val onboardingDescriptionRes: Int? = null
    override val module = onboardingTargetWeightModule
    private val targetVm by viewModel<OnboardingTargetWeightViewModel>()

    override fun onResume() {
        super.onResume()
        updateBMI(vm.getUserWeight(), vm.getUserHeight())
        targetVm.setUserWeight(vm.getUserWeight())
        targetVm.updateWeightInfo()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.weightLayout.weightMainSpinner) {
            setItems(targetVm.mainWeight)
            setOnPositionChangeListener(targetVm::updateSelectedMainWeight)
        }
        with(binding.weightLayout.weightSecondarySpinner) {
            setItems(targetVm.secondaryWeight)
            setOnPositionChangeListener(targetVm::updateSelectedSecondaryWeight)
        }

        targetVm.weightInfo.observe {
            binding.targetWeightInfoTv.text = it.text
            binding.targetWeightInfoTv.setTextColor(requireContext().color(it.textColorRes))
        }
    }

    private fun updateBMI(userWeight: Double, userHeight: Int) {
        val userBMI = targetVm.calculateBMI(userWeight, userHeight)
        val description = requireContext().getString(
            R.string.onboarding_target_weight_description,
            userBMI.bmi?.formatToWeight(),
            userBMI.text
        )
        val spannable = SpannableString(description)
        val bmiLength = userBMI.bmi.toString().length
        spannable.span(
            requireContext().color(userBMI.textColorRes),
            BMI_START,
            BMI_START + bmiLength
        )
        spannable.span(
            requireContext().color(userBMI.textColorRes),
            BMI_START + bmiLength + AFTER_BMI,
            spannable.length
        )
        setOnboardingDescription(spannable)
    }

    override fun onNextButtonClick() {
        vm.setUserTargetWeight(targetVm.selectedWeight.value!!)
    }

    override fun nextButtonVisibilityAction() {
        vm.setNextButtonVisibility(true)
    }

    companion object {
        private const val BMI_START = 12
        private const val AFTER_BMI = 10
    }
}