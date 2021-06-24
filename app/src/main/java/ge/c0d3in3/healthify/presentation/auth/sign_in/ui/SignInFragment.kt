package ge.c0d3in3.healthify.presentation.auth.sign_in.ui

import android.os.Bundle
import android.view.View
import ge.c0d3in3.healthify.R
import ge.c0d3in3.healthify.base.BaseFragment
import ge.c0d3in3.healthify.databinding.SignInFragmentBinding
import ge.c0d3in3.healthify.presentation.auth.sign_in.di.signInModule
import ge.c0d3in3.healthify.presentation.auth.sign_in.vm.SignInViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SignInFragment: BaseFragment<SignInFragmentBinding, SignInViewModel>(SignInFragmentBinding::inflate) {

    override val showBackButton = true
    override val toolbarTitleRes = R.string.sign_in
    override val module = signInModule
    override val vm by viewModel<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.signUpTv.setOnClickListener {
            navigateTo(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.signInBtn.setOnClickListener {
            val email = binding.mailEt.text
            val password = binding.passwordEt.text
            vm.signIn(email, password)
        }
    }
}