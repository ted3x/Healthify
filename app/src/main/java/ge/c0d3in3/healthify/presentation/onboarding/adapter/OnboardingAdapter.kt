package ge.c0d3in3.healthify.presentation.onboarding.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ge.c0d3in3.healthify.presentation.onboarding.screens.first_name.OnboardingFirstNameFragment
import ge.c0d3in3.healthify.presentation.onboarding.ui.OnboardingFragment

class OnboardingAdapter(parentFragment: Fragment, private val fragments: List<Fragment>) :
    FragmentStateAdapter(parentFragment) {

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]
}