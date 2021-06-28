package ge.c0d3in3.healthify.presentation.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import ge.c0d3in3.components.edit_text.EditTextWithTitle
import ge.c0d3in3.healthify.R
import ge.c0d3in3.healthify.base.BaseFragment
import ge.c0d3in3.healthify.databinding.ProfileFragmentBinding
import ge.c0d3in3.healthify.presentation.profile.di.profileModule
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment :
    BaseFragment<ProfileFragmentBinding, ProfileViewModel>(ProfileFragmentBinding::inflate) {

    override val showBackButton = true
    override val toolbarTitleRes = R.string.profile_profile
    override val module = profileModule
    override val vm by viewModel<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            updateProfilePictureTv.setOnClickListener { changeProfilePicture() }
            changePasswordTv.setOnClickListener {
                navigateTo(R.id.action_profileFragment_to_changePasswordFragment)
            }
            logOut.setOnClickListener { logOut() }
            statisticsTv.setOnClickListener {
                navigateTo(R.id.action_profileFragment_to_statisticsFragment)
            }
        }

        vm.profilePicture.observe {
            Glide.with(requireContext()).load(it).circleCrop().into(binding.profileIv)
        }
    }

    private fun changeProfilePicture() {
        with(getDialog()) {
            setDialogTitle("Change profile picture")
            setDialogMessage("Enter picture's url")
            setPositiveButtonText("Change")
            setNegativeButtonText("Close")
            setOnNegativeClickListener { dismiss() }
            setEditText(
                hint = "Url",
                title = "Picture url",
                inputType = EditTextWithTitle.TYPE_CLASS_TEXT
            ) {
                if (it.isBlank())
                    Toast.makeText(requireContext(), "Url is blank!", Toast.LENGTH_SHORT).show()
                else
                    vm.changeProfilePicture(it)
            }
            show()
        }
    }

    private fun logOut() {
        with(getDialog()) {
            setDialogTitle("Log out")
            setDialogMessage("Do you really want to log out?")
            setPositiveButtonText("Yes")
            setOnPositiveClickListener {
                vm.logOut()
                dismiss()
            }
            setNegativeButtonText("No")
            setOnNegativeClickListener { dismiss() }
            show()
        }
    }
}