package com.dagger.realtimechart

import android.os.Bundle
import android.widget.Toast
import com.dagger.realtimechart.base.BaseActivity
import com.dagger.realtimechart.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator.View, CustomLineChartMoreLoadListener {
    override val viewModel: MainViewModel by inject()
    override val layoutResourceId: Int get() = R.layout.activity_main

    lateinit var progress: Progress

    var page = 0

    override fun initView(savedInstanceState: Bundle?) {
        viewModel.setNavigator(this@MainActivity)

        viewDataBinding.run {
            lifecycleOwner = this@MainActivity
            activity = this@MainActivity
            vm = viewModel
        }

        progress = Progress(this@MainActivity)
        viewDataBinding.lineChart.setOnMorePageListener(this@MainActivity)
    }

    override fun onProcess() {
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.isProgress.observe(this) {
            when(it) {
                true -> showProgress()
                false -> dismissProgress()
            }
        }

        viewModel.batteryUsageEntryData.observe(this) {
//            initLineChart(entry = it)
            viewDataBinding.lineChart.setData(it, viewModel.xAxisValue)
            page++
        }

        viewModel.moveLast.observe(this) {
            when(it) {
                true -> {
                    viewDataBinding.lineChart.moveEndOfTheChart()
                }
                false -> {
                    viewDataBinding.lineChart.stayTheChart()
                }
            }
        }

        viewModel.isEmptyData.observe(this) {
            if(it) Toast.makeText(this, "더이상 데이터가 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun invalidateFinish() {
        viewDataBinding.lineChart.stayTheChart()
    }

    private fun showProgress() {
        if(!progress.isShowing) progress.show()
    }

    private fun dismissProgress() {
        if(progress.isShowing) progress.dismiss()
    }

    override fun moreLoad() {
        viewModel.fetchData(page = page, period = "day", isFirst = false)
    }

    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }
}