package ge.c0d3in3.healthify.presentation.auth.sign_in.vm

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import ge.c0d3in3.healthify.R
import ge.c0d3in3.healthify.base.BaseViewModel
import ge.c0d3in3.healthify.extensions.execute
import ge.c0d3in3.healthify.repository.UserRepository
import ge.c0d3in3.healthify.presentation.auth.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(
    app: Application,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) :
    BaseViewModel(app) {
    fun signIn(email: String, password: String) {
        if (email.isBlank() || password.isBlank())
            showAlert(R.string.components_error, R.string.components_fill_all_the_fields)
        else
            firebaseSignIn(email, password)
    }

    private fun firebaseSignIn(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.signIn(email, password).execute(
                onSuccess = {
                    if (it.user == null)
                        showAlert(R.string.components_error, R.string.sign_in_incorrect_credentials)
                    else
                        next(it.user!!)
                }, onFail = {
                    showAlert(R.string.components_error, it)
                })
        }
    }

    private suspend fun next(firebaseUser: FirebaseUser) {
        userRepository.getUserData(firebaseUser.uid).execute(
            onSuccess = {
                userRepository.saveUser(it)
                navigateTo(R.id.action_signInFragment_to_dashboardFragment)
            }, onFail = {
                navigateTo(R.id.action_signInFragment_to_onboardingFragment)
            })
    }
}