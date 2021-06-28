package ge.c0d3in3.healthify.presentation.auth.password_reset

import android.app.Application
import androidx.lifecycle.viewModelScope
import ge.c0d3in3.healthify.R
import ge.c0d3in3.healthify.base.BaseViewModel
import ge.c0d3in3.healthify.extensions.execute
import ge.c0d3in3.healthify.presentation.auth.repository.AuthRepository
import kotlinx.coroutines.launch

class PasswordResetViewModel(app: Application, private val authRepository: AuthRepository) :
    BaseViewModel(app) {
    fun resetPassword(mail: String) {
        viewModelScope.launch {
            authRepository.resetPassword(mail).execute(
                onSuccess = {
                    navigateToSignIn(true, "Email sent!")
                }, onFail = {

                }
            )
        }
    }

    private fun navigateToSignIn(isSuccess: Boolean, message: String) {
        showAlert(
            if (isSuccess) R.string.components_success else R.string.components_error,
            message
        )
        navigateTo(R.id.action_passwordResetFragment_to_signInFragment)
    }
}