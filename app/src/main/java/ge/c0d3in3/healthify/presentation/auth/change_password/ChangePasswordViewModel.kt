package ge.c0d3in3.healthify.presentation.auth.change_password

import android.app.Application
import androidx.lifecycle.viewModelScope
import ge.c0d3in3.healthify.R
import ge.c0d3in3.healthify.base.BaseViewModel
import ge.c0d3in3.healthify.extensions.execute
import ge.c0d3in3.healthify.presentation.auth.repository.AuthRepository
import kotlinx.coroutines.launch

class ChangePasswordViewModel(app: Application, private val authRepository: AuthRepository) :
    BaseViewModel(app) {

    fun changePassword(oldPassword: String, oldPasswordConfirm: String, newPassword: String) {
        if (oldPassword != oldPasswordConfirm) return showAlert(R.string.components_error, "old passwords aren't same!")
        if (newPassword.length < 6 || newPassword.length > 32) return showAlert(
            R.string.components_error,
            "New password must be in range of 6-32"
        )
        viewModelScope.launch {
            authRepository.changePassword(newPassword).execute(
                onSuccess = {
                    navigateToProfile(true, "Password changed succesfully")
                },
                onFail = {
                    navigateToProfile(false, it)
                }
            )
        }
    }

    private fun navigateToProfile(isSuccess: Boolean, message: String) {
        showAlert(
            if (isSuccess) R.string.components_success else R.string.components_error,
            message
        )
        navigateTo(R.id.action_changePasswordFragment_to_profileFragment)
    }
}