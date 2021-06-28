package ge.c0d3in3.healthify.presentation.profile.statistic

import android.view.LayoutInflater
import android.view.ViewGroup
import ge.c0d3in3.components.BaseAdapter
import ge.c0d3in3.healthify.databinding.StatisticItemBinding
import ge.c0d3in3.healthify.extensions.gone
import ge.c0d3in3.healthify.extensions.show

class StatisticsAdapter : BaseAdapter<StatisticItem, BaseAdapter.BaseViewHolder<StatisticItemBinding>>() {
    override fun getBinding(parent: ViewGroup) =
        StatisticItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun onBind(
        holder: BaseViewHolder<StatisticItemBinding>,
        item: StatisticItem,
        position: Int
    ) {
        with(holder.binding){
            statisticIv.setBackgroundResource(item.iconRes)
            statisticTv.text = item.name
            progressTv.text = "${item.step}/${item.target}"
            if(item.step >= item.target)
                isCompletedIv.show()
            else isCompletedIv.gone()
        }
    }
}