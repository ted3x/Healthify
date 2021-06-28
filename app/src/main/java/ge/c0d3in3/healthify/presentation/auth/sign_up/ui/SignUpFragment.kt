package ge.c0d3in3.healthify.presentation.auth.sign_up.ui

import android.os.Bundle
import android.view.View
import ge.c0d3in3.healthify.R
import ge.c0d3in3.healthify.base.BaseFragment
import ge.c0d3in3.healthify.databinding.SignUpFragmentBinding
import ge.c0d3in3.healthify.presentation.auth.sign_up.di.signUpModule
import ge.c0d3in3.healthify.presentation.auth.sign_up.vm.SignUpViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SignUpFragment : BaseFragment<SignUpFragmentBinding, SignUpViewModel>(SignUpFragmentBinding::inflate) {

    override val showBackButton = true
    override val toolbarTitleRes = R.string.sign_up
    override val module = signUpModule
    override val vm by viewModel<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.signInBtn.setOnClickListener {
            val email = binding.mailEt.text
            val password = binding.passwordEt.text
            val confirmPassword = binding.confirmPasswordEt.text
            vm.signUp(email, password, confirmPassword)
        }
    }
}