package ge.c0d3in3.healthify.presentation.auth.change_password

import android.os.Bundle
import android.view.View
import ge.c0d3in3.healthify.R
import ge.c0d3in3.healthify.base.BaseFragment
import ge.c0d3in3.healthify.databinding.ChangePasswordFragmentBinding
import ge.c0d3in3.healthify.presentation.auth.change_password.di.changePasswordModule
import org.koin.android.viewmodel.ext.android.viewModel

class ChangePasswordFragment : BaseFragment<ChangePasswordFragmentBinding, ChangePasswordViewModel>(
    ChangePasswordFragmentBinding::inflate
) {
    override val showBackButton = true
    override val toolbarTitleRes = R.string.profile_change_password
    override val module = changePasswordModule
    override val vm by viewModel<ChangePasswordViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.changePasswordBtn.setOnClickListener {
            val oldPwd = binding.oldPasswordEt.text
            val oldConfirmPwd = binding.confirmOldPasswordEt.text
            val newPwd = binding.newPasswordEt.text
            vm.changePassword(oldPwd, oldConfirmPwd, newPwd)
        }
    }
}