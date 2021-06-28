package ge.c0d3in3.components.water_tracker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import ge.c0d3in3.components.DashboardItemView
import ge.c0d3in3.components.databinding.WaterTrackerViewBinding

class WaterTrackerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : DashboardItemView(context, attrs, defStyleAttr) {

    private var callback: (() -> Unit) ? = null
    private val binding by lazy {
        WaterTrackerViewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    init {
        setTitle("Water Tracker")
        setBottomView(binding.root)
        binding.drinkWater.setOnClickListener {
            callback?.invoke()
        }
    }

    override fun setSteps(steps: Double) {
        super.setSteps(steps)
        binding.waterProgressLine.setmValueText(steps.toInt().toString())
        binding.waterProgressLine.setmPercentage(getProgress(steps))
    }

    override fun setTarget(target: Double) {
        super.setTarget(target)
        binding.waterTargetTv.text = target.toInt().toString()
    }

    fun setOnDrinkListener(callback: () -> Unit){
        this.callback = callback
    }
}