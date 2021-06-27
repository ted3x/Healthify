package ge.c0d3in3.healthify.presentation.onboarding.screens.age

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import ge.c0d3in3.components.spinner.RecyclerSpinnerAdapter
import ge.c0d3in3.healthify.R
import ge.c0d3in3.healthify.databinding.OnboardingAgeFragmentBinding
import ge.c0d3in3.healthify.presentation.onboarding.screens.BaseOnboardingFragment

class OnboardingAgeFragment :
    BaseOnboardingFragment<OnboardingAgeFragmentBinding>(OnboardingAgeFragmentBinding::inflate) {
    override val onboardingTitleRes = R.string.onboarding_age_title
    override val onboardingDescriptionRes = R.string.onboarding_age_description

    private val ages = IntRange(13,100).toList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ageSpinner.setItems(ages)
    }

    override fun nextButtonVisibilityAction() {
        vm.setNextButtonVisibility(true)
    }

    override fun onNextButtonClick() {
        val selectedAge = ages[binding.ageSpinner.selectedPosition]
        vm.setUserAge(selectedAge)
    }
}