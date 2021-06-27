package ge.c0d3in3.components

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<Item: Any, VH : BaseAdapter.BaseViewHolder<*>>: RecyclerView.Adapter<VH>() {

    abstract fun getBinding(parent: ViewGroup): ViewBinding
    private var items: List<Item> = listOf()

    abstract fun onBind(holder: VH, item: Item, position: Int)

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        BaseViewHolder(getBinding(parent)) as VH

    override fun onBindViewHolder(holder: VH, position: Int) {
        onBind(holder, items[position], position)
    }

    fun setItems(items: List<Item>) {
        this.items= items
        notifyDataSetChanged()
    }

    open class BaseViewHolder<VB: ViewBinding>(val binding: VB): RecyclerView.ViewHolder(binding.root)
}