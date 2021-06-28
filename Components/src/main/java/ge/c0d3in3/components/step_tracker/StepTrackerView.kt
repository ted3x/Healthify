package ge.c0d3in3.components.step_tracker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import ge.c0d3in3.components.DashboardItemView
import ge.c0d3in3.components.databinding.StepTrackerViewBinding

class StepTrackerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : DashboardItemView(context, attrs, defStyleAttr) {

    private val binding by lazy {
        StepTrackerViewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    init {
        setTitle("Step Tracker")
        setBottomView(binding.root)
    }

    override fun setSteps(steps: Double) {
        super.setSteps(steps)
        binding.stepProgressLine.setmValueText(steps.toInt().toString())
        binding.stepProgressLine.setmPercentage(getProgress(steps))
        binding.kilometersProgressLine.setStepCountText(String.format("%.3f", steps.toKilometers()))
    }

    override fun setTarget(target: Double) {
        super.setTarget(target)
        binding.stepTargetTv.text = target.toInt().toString()
    }

    private fun Double.toKilometers() = 0.0008 * this
}