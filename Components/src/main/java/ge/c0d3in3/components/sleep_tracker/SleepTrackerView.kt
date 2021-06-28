package ge.c0d3in3.components.sleep_tracker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import ge.c0d3in3.components.AlertDialog
import ge.c0d3in3.components.DashboardItemView
import ge.c0d3in3.components.databinding.SleepTrackerViewBinding
import ge.c0d3in3.components.edit_text.EditTextWithTitle
import ge.c0d3in3.components.formatToWeight
import ge.c0d3in3.components.toast

class SleepTrackerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : DashboardItemView(context, attrs, defStyleAttr) {

    private var callback: ((Int) -> Unit)? = null
    private val binding by lazy {
        SleepTrackerViewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    init {
        setTitle("Sleep Tracker")
        setBottomView(binding.root)
        binding.addSleep.setOnClickListener {
            with(AlertDialog(context)) {
                setDialogTitle("Add sleep")
                setEditText(
                    hint = "Hours",
                    title = "Sleeped hours",
                    inputType = EditTextWithTitle.TYPE_CLASS_NUMBER
                ) {
                    if(onWeightGainAdd(it)) dismiss()
                }
                setNegativeButtonText("Close")
                setPositiveButtonText("Add")
                setOnPositiveClickListener {}
                setOnNegativeClickListener { dismiss() }
                show()
            }
        }
    }

    override fun setSteps(steps: Double) {
        super.setSteps(steps)
        binding.sleepProgressLine.setmValueText(steps.toInt())
        binding.sleepProgressLine.setmPercentage(getProgress(steps))
    }

    override fun setTarget(target: Double) {
        super.setTarget(target)
        binding.sleepTargetTv.text = "${target.toInt()}h"
    }

    fun setOnSleepAddListener(callback: (Int) -> Unit) {
        this.callback = callback
    }

    private fun onWeightGainAdd(input: String): Boolean{
        if (input.isBlank()) {
            context.toast("Input is empty!")
            return false
        }
        val sleepedHours = input.toInt()
        if (sleepedHours !in 0..18){
            context.toast("Sleep hours must be in range of 0 - 18")
            return false
        }
        callback?.invoke(sleepedHours)
        return true
    }
}