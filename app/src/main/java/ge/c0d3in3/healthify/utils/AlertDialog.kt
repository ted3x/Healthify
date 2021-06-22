package ge.c0d3in3.healthify.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import ge.c0d3in3.healthify.databinding.AlertDialogViewBinding

class AlertDialog(context: Context) : Dialog(context) {

    private val binding = AlertDialogViewBinding.inflate(LayoutInflater.from(context))
    private var _context: Context? = context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    fun setDialogTitle(titleRes: Int) {
        binding.alertTitleMessage.text = context.getString(titleRes)
    }

    fun setDialogMessage(messageRes: Int) {
        binding.alertMessage.text = context.getString(messageRes)
    }

    fun setNegativeButtonText(textRes: Int) {
        binding.alertNegativeText.text = context.getString(textRes)
    }

    fun setPositiveButtonText(textRes: Int) {
        binding.alertPositiveText.text = context.getString(textRes)
    }

    fun setOnPositiveClickListener(callback: () -> Unit) {
        with(binding.alertPositiveButton) {
            show()
            setOnClickListener { callback.invoke() }
        }
    }

    fun setOnNegativeClickListener(callback: () -> Unit) {
        with(binding.alertNegativeButton) {
            show()
            setOnClickListener { callback.invoke() }
        }
    }

    override fun dismiss() {
        super.dismiss()
        _context = null
    }
}