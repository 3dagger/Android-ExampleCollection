package com.dagger.realtimechart

import android.os.Bundle
import com.dagger.realtimechart.base.BaseActivity
import com.dagger.realtimechart.databinding.ActivityMainBinding
import com.github.mikephil.charting.charts.LineChart
import com.orhanobut.logger.Logger
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator.View, CustomLineChartMoreLoadListener {
    override val viewModel: MainViewModel by inject()
    override val layoutResourceId: Int get() = R.layout.activity_main

    lateinit var progress: Progress
    private lateinit var lineChart: CustomLineChart

    var page = 0

    override fun initView(savedInstanceState: Bundle?) {
        viewModel.setNavigator(this@MainActivity)

        viewDataBinding.run {
            lifecycleOwner = this@MainActivity
            activity = this@MainActivity
            vm = viewModel
        }

        progress = Progress(this@MainActivity)
//        lineChart = CustomLineChart(this, )
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

//        viewModel.moveLast.observe(this) {
//            if(it) {
//                viewModel.setMoveLast(false)
//                moveChartEnd()
//            }
//        }
    }

//    private fun initLineChart(entry: LinkedList<Entry>) {
//        lineChart = viewDataBinding.lineChart
//
//        lineChart.apply {
//            onChartGestureListener = this@MainActivity
//            setTouchEnabled(true)
//            isHighlightPerTapEnabled = false
//            description.isEnabled = false
//            legend.isEnabled = false
//            setPinchZoom(false)
//            setDrawGridBackground(false)
//            setScaleEnabled(false)
//
//
//
//            setExtraOffsets(40f,35f,40f,20f)
//            setBackgroundColor(Color.LTGRAY)
//
//            val data = LineData()
//            data.setValueTextColor(Color.WHITE)
//            setData(data)
//
//            axisLeft.apply {
//                setDrawLabels(false)
//                setDrawGridLines(false)
//                setDrawAxisLine(false)
//                setDrawBorders(false)
//            }
//            axisRight.isEnabled = false
//
//            xAxis.run {
//                position = XAxis.XAxisPosition.BOTTOM
//                setDrawAxisLine(false)
//                setDrawGridLines(true)
//
//                isHighlightPerDragEnabled = false
//                isHighlightPerTapEnabled = false
//
////                setAvoidFirstLastClipping(true)
//                //label 움직이게 하기 위해 주석 처리
////                setLabelCount(7, true)
//                gridLineWidth = 1f
//                gridColor = Color.parseColor("#f4f4f4")
//                textColor = Color.parseColor("#adadad")
//                textSize = 10f
//                typeface = ResourcesCompat.getFont(this@MainActivity, R.font.notosanskrmedium)
//                valueFormatter = XAxisFormatter(viewModel.xAxisValue)
//            }
//        }
//
//        val dataSet = LineDataSet(entry, "a").apply {
//            color = Color.parseColor("#0091c1")
//            setCircleColor(Color.parseColor("#0091c1"))
//            circleHoleColor = Color.parseColor("#FFFFFF")
//            circleRadius = 4f
//            circleHoleRadius = 2f
//            lineWidth = 2f
//            fillDrawable = ContextCompat.getDrawable(this@MainActivity, R.drawable.line_chart_gradient)
//            valueTextSize = 10f
//            valueTextColor = Color.parseColor("#3c3c3c")
//            valueTypeface = ResourcesCompat.getFont(this@MainActivity, R.font.notosanskrbold)
//            valueFormatter = YAxisFormatter()
//            setDrawFilled(true)
//            setDrawValues(true)
//        }
//
//        val dataSetArray: ArrayList<ILineDataSet> = ArrayList()
//        dataSetArray.add(dataSet)
//
//        lineChart.run {
//            clear()
//            this.data = LineData(dataSetArray)
//            this.setVisibleXRangeMaximum(6f)
//            invalidate()
//        }
//    }

    fun moveChartEnd() {
        lineChart.run {
            moveViewToX(data?.entryCount!!.toFloat())
        }
    }

    fun moveChartNext() {
        lineChart.moveViewToX(7F)
    }

    fun moveChartPrevious() {
//        viewModel.fetchData(page = "1", isFirst = false)
        viewModel.fetchData(page = page, period = "day", isFirst = false)
//        viewModel.fetchData(page = page, period = "month", isFirst = false)
    }

    fun refresh() {
        viewModel.fetchData(period = "day", page = 0, isFirst = true)
    }

    fun showProgress() {
        if(!progress.isShowing) progress.show()
    }

    fun dismissProgress() {
        if(progress.isShowing) progress.dismiss()
    }

    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }

    override fun moreLoad() {
        Logger.d("moreLoad")
    }
}