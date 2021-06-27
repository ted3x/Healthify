package ge.c0d3in3.healthify.presentation.onboarding.ui

import android.os.Bundle
import android.text.SpannableString
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import ge.c0d3in3.healthify.R
import ge.c0d3in3.healthify.base.BaseFragment
import ge.c0d3in3.healthify.databinding.OnboardingFragmentBinding
import ge.c0d3in3.healthify.presentation.onboarding.adapter.OnboardingAdapter
import ge.c0d3in3.healthify.presentation.onboarding.di.onboardingModule
import ge.c0d3in3.healthify.presentation.onboarding.screens.age.OnboardingAgeFragment
import ge.c0d3in3.healthify.presentation.onboarding.screens.first_name.OnboardingFirstNameFragment
import ge.c0d3in3.healthify.presentation.onboarding.screens.height.OnboardingHeightFragment
import ge.c0d3in3.healthify.presentation.onboarding.screens.last_name.OnboardingLastNameFragment
import ge.c0d3in3.healthify.presentation.onboarding.screens.profile_picture.OnboardingProfilePictureFragment
import ge.c0d3in3.healthify.presentation.onboarding.screens.target_weight.OnboardingTargetWeightFragment
import ge.c0d3in3.healthify.presentation.onboarding.screens.weight.OnboardingWeightFragment
import ge.c0d3in3.healthify.presentation.onboarding.vm.OnboardingViewModel
import ge.c0d3in3.healthify.utils.gone
import ge.c0d3in3.healthify.utils.show
import org.koin.android.viewmodel.ext.android.sharedViewModel

class OnboardingFragment : BaseFragment<OnboardingFragmentBinding, OnboardingViewModel>(
    OnboardingFragmentBinding::inflate
) {
    override val showBackButton = false
    override val toolbarTitleRes = R.string.onboarding
    override val module = onboardingModule
    override val vm by sharedViewModel<OnboardingViewModel>()

    private val fragments = listOf(
        OnboardingFirstNameFragment(),
        OnboardingLastNameFragment(),
        OnboardingAgeFragment(),
        OnboardingHeightFragment(),
        OnboardingWeightFragment(),
        OnboardingTargetWeightFragment(),
        OnboardingProfilePictureFragment()
    )

    private var pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            if (position > 0) binding.backBtn.show()
            else binding.backBtn.gone()
            with(fragments[position]) {
                binding.onboardingTitle.setText(onboardingTitleRes)
                onboardingDescriptionRes?.let { binding.onboardingDescription.setText(it) }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextBtn.isEnabled = false
        setViewPager()
        setObservers()
        setClickListeners()
    }

    private fun setViewPager() {
        val adapter = OnboardingAdapter(this, fragments)
        with(binding.onboardingViewPager){
            this.adapter = adapter
            isUserInputEnabled = false
            offscreenPageLimit = fragments.size
            registerOnPageChangeCallback(pageChangeCallback)
            binding.onboardingIndicator.setViewPager2(this)
        }
    }

    private fun setClickListeners() {
        binding.nextBtn.setOnClickListener {
            val currItem = binding.onboardingViewPager.currentItem
            val fragment = fragments[currItem]
            fragment.onNextButtonClick()
            binding.onboardingViewPager.currentItem = currItem + 1
        }

        binding.backBtn.setOnClickListener {
            binding.onboardingViewPager.currentItem = binding.onboardingViewPager.currentItem - 1
        }
    }

    private fun setObservers(){
        vm.nextButtonVisibility.observe {
            binding.nextBtn.isEnabled = it
        }

        vm.navigateToDashboard.observe {
            navigateTo(R.id.action_onboardingFragment_to_dashboardFragment)
        }
    }

    fun setOnboardingDescription(description: SpannableString){
        binding.onboardingDescription.text = description
    }

    override fun onDestroyView() {
        binding.onboardingViewPager.unregisterOnPageChangeCallback(pageChangeCallback)
        binding.onboardingViewPager.adapter = null
        super.onDestroyView()
    }

    interface OnboardingListener {
        fun onNextButtonClick()
    }
}