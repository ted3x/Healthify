package ge.c0d3in3.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import ge.c0d3in3.components.databinding.DashboardItemViewLayoutBinding

open class DashboardItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var callback: ((Boolean) -> Unit)? = null
    private var target: Double = 1.0

    private val binding by lazy {
        DashboardItemViewLayoutBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setTitle(title: String) {
        binding.titleTv.text = title
    }
    
    fun setBottomView(view: View) {
        if(view.parent != null)
            (view.parent as ViewGroup).removeView(view)
        binding.bottomView.removeAllViews()
        binding.bottomView.addView(view)
    }

    private fun setCompletionVisibility(visible: Boolean){
        with(binding) {
            if(visible) {
                completionLayout.root.visibility = View.VISIBLE
                bottomView.visibility = View.GONE
            }
            else{
                completionLayout.root.visibility = View.GONE
                bottomView.visibility = View.VISIBLE
            }
        }
    }

    private fun checkCompletion(steps: Double) {
        if (steps >= target) {
            setCompletionVisibility(visible = true)
            callback?.invoke(true)
        }
        else {
            setCompletionVisibility(visible = false)
            callback?.invoke(false)
        }
    }
    open fun setSteps(steps: Double) {
        checkCompletion(steps)
    }

    open fun setTarget(target: Double) {
        this.target = target
    }

    fun onCompletion(callback: (Boolean) -> Unit) {
        this.callback = callback
    }

    fun getProgress(steps: Double): Int = if (steps < 1) 0 else ((steps * 100) / target).toInt()

}