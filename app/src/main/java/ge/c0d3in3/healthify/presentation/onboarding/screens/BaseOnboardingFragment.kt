package ge.c0d3in3.healthify.presentation.onboarding.screens

import android.os.Bundle
import android.text.SpannableString
import android.view.View
import androidx.viewbinding.ViewBinding
import ge.c0d3in3.healthify.base.BaseFragment
import ge.c0d3in3.healthify.base.Inflate
import ge.c0d3in3.healthify.presentation.onboarding.ui.OnboardingFragment
import ge.c0d3in3.healthify.presentation.onboarding.vm.OnboardingViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.dsl.module

abstract class BaseOnboardingFragment<VB: ViewBinding>(inflate: Inflate<VB>) :
    BaseFragment<VB, OnboardingViewModel>(inflate), OnboardingFragment.OnboardingListener {

    private var fragmentCreated = false
    abstract val onboardingTitleRes: Int
    abstract val onboardingDescriptionRes: Int?
    override val module = module {  }
    override val vm by sharedViewModel<OnboardingViewModel>()

    override fun onResume() {
        super.onResume()
        if(fragmentCreated) nextButtonVisibilityAction()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(!fragmentCreated) {
            nextButtonVisibilityAction()
            fragmentCreated = true
        }
    }

    protected fun setOnboardingDescription(description: SpannableString) {
        (parentFragment as? OnboardingFragment)?.setOnboardingDescription(description)
    }
    abstract fun nextButtonVisibilityAction()
}