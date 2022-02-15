package com.dagger.realtimechart

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.ChartTouchListener
import com.github.mikephil.charting.listener.OnChartGestureListener
import com.orhanobut.logger.Logger
import java.util.*
import kotlin.math.roundToInt

class CustomLineChart @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr),
    OnChartGestureListener {
    private val lineChart = LineChart(context)
    private var listener: CustomLineChartMoreLoadListener? = null

    init {
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        lineChart.layoutParams = params
        this.addView(lineChart)


        setupChart()
    }

    fun setOnListener(listener: CustomLineChartMoreLoadListener) {
        this.listener = listener
    }

    private fun setupChart() {
        lineChart.apply {
            onChartGestureListener = this@CustomLineChart
            setTouchEnabled(true)
            isHighlightPerTapEnabled = false
            isHighlightPerDragEnabled = false
            description.isEnabled = false
            legend.isEnabled = false
            setPinchZoom(false)
            setDrawGridBackground(false)
            setScaleEnabled(false)
            setExtraOffsets(40f,35f,40f,20f)
            setBackgroundColor(Color.LTGRAY)

            axisLeft.apply {
                setDrawLabels(false)
                setDrawGridLines(false)
                setDrawAxisLine(false)
                setDrawBorders(false)
            }
            axisRight.isEnabled = false

            xAxis.run {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawAxisLine(false)
                setDrawGridLines(true)
                isHighlightPerDragEnabled = false
                isHighlightPerTapEnabled = false
                gridLineWidth = 1f
                gridColor = Color.parseColor("#f4f4f4")
                textColor = Color.parseColor("#adadad")
                textSize = 10f
                typeface = ResourcesCompat.getFont(context, R.font.notosanskrmedium)
//                valueFormatter = XAxisFormatter(viewModel.xAxisValue)
//                valueFormatter = XAxisFormatter()
            }
        }
    }

    fun setData(data: LinkedList<Entry>, xValue: LinkedList<String>) {
        val firstData = createLineChart(data)

        val dataSetArray: ArrayList<ILineDataSet> = ArrayList()
        dataSetArray.add(firstData)

        lineChart.xAxis.valueFormatter = XAxisFormatter(xValue)
        lineChart.run {
            clear()
            this.data = LineData(dataSetArray)
            this.setVisibleXRangeMaximum(6F)
            invalidate()
        }
    }

    private fun createLineChart(data: LinkedList<Entry>): LineDataSet {
        val lineDataSet = LineDataSet(data, "").apply {
            color = Color.parseColor("#0091c1")
            setCircleColor(Color.parseColor("#0091c1"))
            circleHoleColor = Color.parseColor("#FFFFFF")
            circleRadius = 4f
            circleHoleRadius = 2f
            lineWidth = 2f
            fillDrawable = ContextCompat.getDrawable(context, R.drawable.line_chart_gradient)
            valueTextSize = 10f
            valueTextColor = Color.parseColor("#3c3c3c")
            valueTypeface = ResourcesCompat.getFont(context, R.font.notosanskrbold)
            valueFormatter = YAxisFormatter()
            setDrawFilled(true)
            setDrawValues(true)
        }

        return lineDataSet
    }


    inner class XAxisFormatter(private val xAxisArray: LinkedList<String>) : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return xAxisArray.getOrNull(value.toInt()) ?: value.toString()
        }
    }

    inner class YAxisFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String  {
            return value.roundToInt().toString()
        }
    }

    override fun onChartGestureStart(me: MotionEvent?, lastPerformedGesture: ChartTouchListener.ChartGesture?) {
    }

    override fun onChartGestureEnd(me: MotionEvent?, lastPerformedGesture: ChartTouchListener.ChartGesture?) {
        if(lineChart.visibleXRange.toInt() == lineChart.highestVisibleX.toInt()) {
            Logger.d("end point called")
//            viewModel.fetchData(page = page, period = "day", isFirst = false)
            listener?.moreLoad()
        }
    }

    override fun onChartLongPressed(me: MotionEvent?) {
    }

    override fun onChartDoubleTapped(me: MotionEvent?) {
    }

    override fun onChartSingleTapped(me: MotionEvent?) {
    }

    override fun onChartFling(me1: MotionEvent?, me2: MotionEvent?, velocityX: Float, velocityY: Float) {
    }

    override fun onChartScale(me: MotionEvent?, scaleX: Float, scaleY: Float) {
    }

    override fun onChartTranslate(me: MotionEvent?, dX: Float, dY: Float) {
    }
}