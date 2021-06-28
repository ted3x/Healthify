package ge.c0d3in3.components.weight_tracker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Toast
import ge.c0d3in3.components.AlertDialog
import ge.c0d3in3.components.DashboardItemView
import ge.c0d3in3.components.databinding.WeightTrackerViewBinding
import ge.c0d3in3.components.edit_text.EditTextWithTitle
import ge.c0d3in3.components.formatToWeight
import ge.c0d3in3.components.toast

class WeightTrackerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : DashboardItemView(context, attrs, defStyleAttr) {

    private var callback: ((Double) -> Unit)? = null

    private val binding by lazy {
        WeightTrackerViewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    init {
        setTitle("Weight Tracker")
        setBottomView(binding.root)
        binding.addWeight.setOnClickListener {
            with(AlertDialog(context)) {
                setDialogTitle("Add weight")
                setEditText(
                    hint = "Gained weight",
                    title = "Weight",
                    inputType = EditTextWithTitle.TYPE_NUMBER_FLAG_DECIMAL
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

    fun setBurnedCalories(calories: Double) {
        binding.caloriesProgressLine.setStepCountText(String.format("%.3f", calories))
    }

    override fun setSteps(steps: Double) {
        super.setSteps(steps)
        binding.weightProgressLine.setmValueText(steps.formatToWeight())
        binding.weightProgressLine.setmPercentage(getProgress(steps))
    }

    override fun setTarget(target: Double) {
        super.setTarget(target)
        binding.weightTargetTv.text = "${target.formatToWeight()} kg"
    }

    fun setOnWeightAddListener(callback: (Double) -> Unit) {
        this.callback = callback
    }

    private fun onWeightGainAdd(input: String): Boolean{
        if (input.isBlank()) {
            context.toast("Input is empty!")
            return false
        }
        val weight = input.toDouble()
        if (weight !in 0.1..10.0){
            context.toast("Weight gain must be in range of 0.1 - 10.0")
            return false
        }
        callback?.invoke(weight)
        return true
    }
}