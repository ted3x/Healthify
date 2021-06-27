package ge.c0d3in3.healthify.presentation.onboarding.screens.profile_picture

import android.os.Bundle
import android.view.View
import ge.c0d3in3.healthify.R
import ge.c0d3in3.healthify.databinding.OnboardingProfilePictureFragmentBinding
import ge.c0d3in3.healthify.presentation.onboarding.screens.BaseOnboardingFragment

class OnboardingProfilePictureFragment :
    BaseOnboardingFragment<OnboardingProfilePictureFragmentBinding>(
        OnboardingProfilePictureFragmentBinding::inflate
    ) {
    override val onboardingTitleRes = R.string.onboarding_profile_picture_title
    override val onboardingDescriptionRes = R.string.onboarding_profile_picture_description

    override fun nextButtonVisibilityAction() {
        vm.setNextButtonVisibility(true)
    }

    override fun onNextButtonClick() {
        vm.setUserProfilePicture(binding.onboardingProfilePictureEt.text)
    }
}