package ge.c0d3in3.healthify.presentation.auth.sign_up.vm

import android.app.Application
import androidx.lifecycle.viewModelScope
import ge.c0d3in3.healthify.R
import ge.c0d3in3.healthify.base.BaseViewModel
import ge.c0d3in3.healthify.extensions.execute
import ge.c0d3in3.healthify.model.User
import ge.c0d3in3.healthify.presentation.auth.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(app: Application, private val authRepository: AuthRepository) :
    BaseViewModel(app = app) {
    fun signUp(email: String, password: String, confirmPassword: String) {
        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank())
            showAlert(R.string.components_error, R.string.components_fill_all_the_fields)
        else
            firebaseSignUp(email, password)
    }

    private fun firebaseSignUp(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.signUp(email, password).execute(
                onSuccess = {
                    if (it.user == null)
                        showAlert(R.string.components_error, R.string.sign_up_error_while_signing_up)
                    else
                        navigateTo(R.id.action_signUpFragment_to_onboardingFragment)
                }, onFail = {
                    showAlert(R.string.components_error, it)
                })
        }
    }
}