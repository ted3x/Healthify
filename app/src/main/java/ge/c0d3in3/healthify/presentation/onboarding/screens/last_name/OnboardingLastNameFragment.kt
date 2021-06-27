package ge.c0d3in3.healthify.presentation.onboarding.screens.last_name

import android.os.Bundle
import android.view.View
import ge.c0d3in3.healthify.R
import ge.c0d3in3.healthify.databinding.OnboardingLastNameFragmentBinding
import ge.c0d3in3.healthify.presentation.onboarding.screens.BaseOnboardingFragment

class OnboardingLastNameFragment :
    BaseOnboardingFragment<OnboardingLastNameFragmentBinding>(OnboardingLastNameFragmentBinding::inflate) {

    override val onboardingTitleRes = R.string.onboarding_last_name_title
    override val onboardingDescriptionRes = R.string.onboarding_last_name_description

    override fun nextButtonVisibilityAction() {
        with(binding.onboardingLastNameEt) {
            vm.setNextButtonVisibility(text.isNotBlank())
            onChange {
                vm.setNextButtonVisibility(it.isNotBlank())
            }
        }
    }

    override fun onNextButtonClick() {
        vm.setUserLastName(binding.onboardingLastNameEt.text)
    }
}