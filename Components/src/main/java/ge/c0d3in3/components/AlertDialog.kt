package ge.c0d3in3.components

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import ge.c0d3in3.components.databinding.AlertDialogViewBinding

class AlertDialog(context: Context) : Dialog(context) {

    private var editTextCallback: ((String) -> Unit)? = null
    private val binding = AlertDialogViewBinding.inflate(LayoutInflater.from(context))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    fun setDialogTitle(titleRes: Int) {
        binding.alertTitleMessage.text = context.getString(titleRes)
    }

    fun setDialogTitle(title: String) {
        binding.alertTitleMessage.text = title
    }

    fun setDialogMessage(messageRes: Int) {
        binding.alertMessage.text = context.getString(messageRes)
    }

    fun setDialogMessage(message: String) {
        binding.alertMessage.text = message
    }

    fun setNegativeButtonText(textRes: Int) {
        binding.alertNegativeText.text = context.getString(textRes)
    }

    fun setNegativeButtonText(text: String) {
        binding.alertNegativeText.text = text
    }

    fun setPositiveButtonText(textRes: Int) {
        binding.alertPositiveText.text = context.getString(textRes)
    }

    fun setPositiveButtonText(text: String) {
        binding.alertPositiveText.text = text
    }

    fun setOnPositiveClickListener(callback: () -> Unit) {
        with(binding.alertPositiveButton) {
            visibility = View.VISIBLE
            setOnClickListener {
                editTextCallback?.invoke(binding.dialogEt.text)
                callback.invoke()
            }
        }
    }

    fun setOnNegativeClickListener(callback: () -> Unit) {
        with(binding.alertNegativeButton) {
            visibility = View.VISIBLE
            setOnClickListener { callback.invoke() }
        }
    }

    fun setEditText(hint: String, title: String, inputType: Int, callback: (String) -> Unit) {
        with(binding.dialogEt) {
            this.visibility = View.VISIBLE
            this.hint = hint
            this.title = title
            this.inputType = inputType
        }
        this.editTextCallback = callback
    }
}