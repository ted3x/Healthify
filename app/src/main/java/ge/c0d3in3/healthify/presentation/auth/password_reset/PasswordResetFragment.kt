package ge.c0d3in3.healthify.presentation.auth.password_reset

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ge.c0d3in3.healthify.R
import ge.c0d3in3.healthify.base.BaseFragment
import ge.c0d3in3.healthify.databinding.PasswordResetFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.module.Module

class PasswordResetFragment :
    BaseFragment<PasswordResetFragmentBinding, PasswordResetViewModel>(PasswordResetFragmentBinding::inflate) {

    override val showBackButton = true
    override val toolbarTitleRes = R.string.sign_in_recover_password
    override val module = passwordResetModule
    override val vm by viewModel<PasswordResetViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.changePasswordBtn.setOnClickListener {
            val mail = binding.emailEt.text
            vm.resetPassword(mail)
        }
    }
}