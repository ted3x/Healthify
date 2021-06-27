package ge.c0d3in3.healthify.presentation.onboarding.screens.first_name

import android.os.Bundle
import android.view.View
import ge.c0d3in3.healthify.R
import ge.c0d3in3.healthify.databinding.OnboardingFirstNameFragmentBinding
import ge.c0d3in3.healthify.presentation.onboarding.screens.BaseOnboardingFragment

class OnboardingFirstNameFragment :
    BaseOnboardingFragment<OnboardingFirstNameFragmentBinding>(OnboardingFirstNameFragmentBinding::inflate) {

    override val onboardingTitleRes = R.string.onboarding_first_name_title
    override val onboardingDescriptionRes = R.string.onboarding_first_name_description

    override fun nextButtonVisibilityAction() {
        with(binding.onboardingFirstNameEt) {
            vm.setNextButtonVisibility(text.isNotBlank())
            onChange {
                vm.setNextButtonVisibility(it.isNotBlank())
            }
        }
    }

    override fun onNextButtonClick() {
        vm.setUserFirstName(binding.onboardingFirstNameEt.text)
    }
}