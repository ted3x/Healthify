package ge.c0d3in3.healthify.presentation.onboarding.screens.height

import android.os.Bundle
import android.view.View
import ge.c0d3in3.healthify.R
import ge.c0d3in3.healthify.databinding.OnboardingHeightFragmentBinding
import ge.c0d3in3.healthify.presentation.onboarding.screens.BaseOnboardingFragment
import org.koin.android.ext.android.bind

class OnboardingHeightFragment : BaseOnboardingFragment<OnboardingHeightFragmentBinding>(
    OnboardingHeightFragmentBinding::inflate) {
    override val onboardingTitleRes = R.string.onboarding_height_title
    override val onboardingDescriptionRes = R.string.onboarding_height_description

    private val heights = IntRange(100,300).toList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.heightSpinner.setItems(heights)
    }

    override fun nextButtonVisibilityAction() {
        vm.setNextButtonVisibility(true)
    }

    override fun onNextButtonClick() {
        val selectedHeight = heights[binding.heightSpinner.selectedPosition]
        vm.setUserHeight(selectedHeight)
    }
}