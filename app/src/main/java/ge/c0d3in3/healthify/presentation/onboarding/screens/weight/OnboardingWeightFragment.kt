package ge.c0d3in3.healthify.presentation.onboarding.screens.weight

import android.os.Bundle
import android.view.View
import androidx.core.util.rangeTo
import androidx.core.util.toClosedRange
import ge.c0d3in3.healthify.R
import ge.c0d3in3.healthify.databinding.OnboardingWeightFragmentBinding
import ge.c0d3in3.healthify.presentation.onboarding.screens.BaseOnboardingFragment

class OnboardingWeightFragment :
    BaseOnboardingFragment<OnboardingWeightFragmentBinding>(OnboardingWeightFragmentBinding::inflate) {
    override val onboardingTitleRes = R.string.onboarding_weight_title
    override val onboardingDescriptionRes = R.string.onboarding_weight_description

    private val mainWeight = IntRange(30, 180).toList()
    private val secondaryWeight = IntRange(0, 9).toList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.weightLayout.weightMainSpinner.setItems(mainWeight)
        binding.weightLayout.weightSecondarySpinner.setItems(secondaryWeight)
    }

    override fun nextButtonVisibilityAction() {
        vm.setNextButtonVisibility(true)
    }

    override fun onNextButtonClick() {
        with(binding.weightLayout) {
            val selectedWeight =
                mainWeight[weightMainSpinner.selectedPosition] +
                        secondaryWeight[weightSecondarySpinner.selectedPosition].toDouble() / 10
            vm.setUserWeight(selectedWeight)
        }
    }
}