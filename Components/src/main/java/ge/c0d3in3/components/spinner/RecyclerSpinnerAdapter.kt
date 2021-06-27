package ge.c0d3in3.components.spinner

import android.view.LayoutInflater
import android.view.ViewGroup
import ge.c0d3in3.components.BaseAdapter
import ge.c0d3in3.components.databinding.SpinnerItemViewBinding

class RecyclerSpinnerAdapter(private val onItemClick: (Int) -> Unit): BaseAdapter<Any, BaseAdapter.BaseViewHolder<SpinnerItemViewBinding>>() {

    override fun getBinding(parent: ViewGroup) =
        SpinnerItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun onBind(
        holder: BaseViewHolder<SpinnerItemViewBinding>,
        item: Any,
        position: Int
    ) {
        holder.binding.spinnerTv.text = item.toString()
        holder.binding.spinnerTv.setOnClickListener {
            onItemClick.invoke(position)
        }
    }
}