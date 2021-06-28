package ge.c0d3in3.healthify.presentation.profile.statistic

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ge.c0d3in3.healthify.R
import ge.c0d3in3.healthify.base.BaseFragment
import ge.c0d3in3.healthify.databinding.StatisticsFragmentBinding
import ge.c0d3in3.healthify.presentation.profile.statistic.di.statisticsModule
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.module.Module

class StatisticsFragment :
    BaseFragment<StatisticsFragmentBinding, StatisticsViewModel>(StatisticsFragmentBinding::inflate) {

    override val showBackButton = true
    override val toolbarTitleRes = R.string.profile_statistics
    override val module = statisticsModule
    override val vm by viewModel<StatisticsViewModel>()
    private lateinit var adapter: StatisticsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = StatisticsAdapter()
        binding.statisticsRv.adapter = adapter
        vm.steps.observe {
            adapter.setItems(it)
        }
        vm.date.observe {
            binding.dateTv.text = it
        }
    }
}