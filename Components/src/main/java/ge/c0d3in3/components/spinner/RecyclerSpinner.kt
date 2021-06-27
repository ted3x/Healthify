package ge.c0d3in3.components.spinner

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import ge.c0d3in3.components.R
import ge.c0d3in3.components.databinding.RecyclerSpinnerLayoutBinding
import ge.c0d3in3.components.databinding.SpinnerItemViewBinding

class RecyclerSpinner @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var selectedPosition = 0
        get() = if(field - EXTRA_ITEMS > 0) field - EXTRA_ITEMS else 0
        private set(value) {
            field = value
            positionChangeListener?.invoke(selectedPosition)
        }

    private val binding by lazy {
        RecyclerSpinnerLayoutBinding.inflate(LayoutInflater.from(context), this, true)
    }
    private val adapter = RecyclerSpinnerAdapter {
        val positionToScroll = if(it > selectedPosition + EXTRA_ITEMS) it + EXTRA_ITEMS else it - EXTRA_ITEMS
        if(positionToScroll < 0) return@RecyclerSpinnerAdapter
        selectedPosition = it
        binding.spinnerRv.smoothScrollToPosition(positionToScroll)
    }

    private var gravity = GRAVITY_CENTER
        set(value) {
            field = value
            (binding.spinnerLayout.layoutParams as LayoutParams).gravity =
                when (value) {
                    GRAVITY_START -> Gravity.START
                    GRAVITY_CENTER -> Gravity.CENTER
                    GRAVITY_END -> Gravity.END
                    else -> Gravity.CENTER
                }
        }

    var positionChangeListener: ((Int) -> Unit)? = null

    init {
        attrs.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.RecyclerSpinner)
            gravity = typedArray.getInt(R.styleable.RecyclerSpinner_rsGravity, GRAVITY_CENTER)
            typedArray.recycle()
        }
        initAdapter()
    }

    private fun initAdapter() {
        binding.spinnerRv.adapter = adapter
        val snaper = PagerSnapHelper()
        snaper.attachToRecyclerView(binding.spinnerRv)
        binding.spinnerRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    selectedPosition = getPosition()
                }
            }
        })
    }

    fun setItems(items: List<Any>, addExtraItems: Boolean = true) {
        val mItems = items.toMutableList()
        if (addExtraItems) {
            val extraItems = (0 until EXTRA_ITEMS).map { "" }
            mItems.addAll(0, extraItems)
            mItems.addAll(extraItems)
        }
        adapter.setItems(mItems)
    }

    fun setOnPositionChangeListener(callback: (Int) -> Unit) {
        positionChangeListener = callback
    }

    private fun getPosition(): Int {
        val lm = binding.spinnerRv.layoutManager as LinearLayoutManager
        return lm.findLastVisibleItemPosition() - (MAX_VISIBLE_ITEMS / 2)
    }

    companion object {
        private const val GRAVITY_START = 0
        private const val GRAVITY_CENTER = 1
        private const val GRAVITY_END = 2
        private const val EXTRA_ITEMS = 2
        private const val MAX_VISIBLE_ITEMS = 5
    }
}
