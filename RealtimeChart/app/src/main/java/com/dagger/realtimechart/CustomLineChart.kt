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
import com.github.mikephil.charting.components.YAxis
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

class CustomLineChart @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr), OnChartGestureListener {
    private val lineChart = LineChart(context)
    private var listener: CustomLineChartMoreLoadListener? = null
    private val utility = Utility(context)

    private var saveCount = 0F

    init {
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        lineChart.layoutParams = params
        this.addView(lineChart)
        setupChart()
        Logger.d("in ?")
    }

    fun setOnMorePageListener(listener: CustomLineChartMoreLoadListener) {
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
//            when(utility.getDeviceWidthDpSize(activity) <= DeviceWidthDpType.Type320Over.value) {
//                true -> setExtraOffsets(35f,35f,35f,20f)
//                false -> setExtraOffsets(40f,35f,40f,20f)
//            }
            when(utility.getDeviceWidthDpSize2() <= 320) {
                true -> setExtraOffsets(35f,35f,35f,20f)
                false -> setExtraOffsets(40f,35f,40f,20f)
            }
            setExtraOffsets(40f,35f,40f,20f)

            axisLeft.apply {
                setDrawLabels(false)
                setDrawGridLines(false)
                setDrawAxisLine(false)
                setDrawBorders(false)
            }
            axisRight.isEnabled = false

            setXAxisRenderer(CustomXAxisRenderer(viewPortHandler, xAxis, getTransformer(YAxis.AxisDependency.LEFT), context))
            renderer = CustomLineChartRenderer(context, this, animator, viewPortHandler)

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
            }

            animateY(300)
        }
    }

    fun setData(data: LinkedList<Entry>, xValue: LinkedList<String>) {
        val firstData = createLineChart(data)
        val linkedList = LinkedList<Entry>()
        linkedList.add(data.last)
        val b = createLastValueLineChart(linkedList)

        val dataSetArray: ArrayList<ILineDataSet> = ArrayList()
        dataSetArray.add(firstData)
        dataSetArray.add(b)

        lineChart.xAxis.valueFormatter = XAxisFormatter(xValue)
        lineChart.run {
            this.data = LineData(dataSetArray)
//            Logger.d("this.data.yMax :: ${this.data.yMax}\nthis.data.yMin :: ${this.data.yMin}\nthis.data.xMax :: ${this.data.xMax}\nthis.data.xMin :: ${this.data.xMin}")
            when {
                this.data.yMax == this.data.yMin        ->  this.axisLeft.axisMinimum = this.data.yMin - 0.05F
                this.data.yMax - this.data.yMin < 10    ->  this.axisLeft.axisMinimum = this.data.yMin - 0.1F
                this.data.yMax - this.data.yMin < 100   ->  this.axisLeft.axisMinimum = this.data.yMin - 1F
                else ->  this.axisLeft.axisMinimum = this.data.yMin - 10F
            }
            this.setVisibleXRangeMaximum(6F)
            setDragDecelerationEnabled(false)
            invalidate()

            // 범위 설정 후 위치 이동 오류나서 추가함
            // 부드러운 애니메이션 효과 설정
//            if(data.size > 20) {
//                isDragDecelerationEnabled = false
//            }
        }
    }

    fun setDragDecelerationEnabled(isEnabled: Boolean) {
        lineChart.isDragDecelerationEnabled = isEnabled
    }

    fun moveEndOfTheChart() {
        saveCount = 0F
        saveCount = lineChart.data?.entryCount!!.toFloat()
        lineChart.run {
            moveViewToX(data?.entryCount!!.toFloat())
        }
    }

    fun stayTheChart() {
        Logger.d("res :: ${lineChart.data.dataSets}")
        lineChart.run {
            moveViewToX(19F)
            isDragDecelerationEnabled = true
//            moveViewToX(data?.entryCount!!.toFloat())
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

//    private fun iCreateLineChart(data: LinkedList<Entry>): CustomLineDataSet {
//        val lineDataSet = CustomLineDataSet(context, data, "").apply {
//            color = Color.parseColor("#0091c1")
//            setCircleColor(Color.parseColor("#0091c1"))
//            circleHoleColor = Color.parseColor("#FFFFFF")
//            circleRadius = 4f
//            circleHoleRadius = 2f
//            lineWidth = 2f
//            fillDrawable = ContextCompat.getDrawable(context, R.drawable.line_chart_gradient)
//            valueTextSize = 10f
//            valueTextColor = Color.parseColor("#3c3c3c")
//            valueTypeface = ResourcesCompat.getFont(context, R.font.notosanskrbold)
//            valueFormatter = YAxisFormatter()
//            setDrawFilled(true)
//            setDrawValues(true)
//        }
//        return lineDataSet
//    }

    private fun createLastValueLineChart(data: LinkedList<Entry>): LineDataSet {
        val lineDataSet = LineDataSet(data, "").apply {
            color = Color.parseColor("#0091c1")
            setCircleColor(Color.parseColor("#0091c1"))
            circleHoleColor = Color.parseColor("#FFFFFF")
            circleRadius = 6f
            circleHoleRadius = 3.5f
            valueTextSize = 0f
            valueTextColor = Color.parseColor("#3c3c3c")
            valueTypeface = ResourcesCompat.getFont(context, R.font.notosanskrbold)
            valueFormatter = YAxisFormatter()
            setDrawFilled(false)
            setDrawValues(false)
        }

        return lineDataSet
    }

    override fun onChartGestureEnd(me: MotionEvent?, lastPerformedGesture: ChartTouchListener.ChartGesture?) {
        if(lineChart.visibleXRange.toInt() == lineChart.highestVisibleX.toInt()) {
            listener?.moreLoad()
        }
    }

    override fun onChartGestureStart(me: MotionEvent?, lastPerformedGesture: ChartTouchListener.ChartGesture?) {
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
}