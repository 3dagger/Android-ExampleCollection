package com.dagger.realtimechart

import android.content.Context
import android.graphics.Color
import android.graphics.DashPathEffect
import com.github.mikephil.charting.data.DataSet
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.LineRadarDataSet
import com.github.mikephil.charting.formatter.DefaultFillFormatter
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class CustomLineDataSet(private val context: Context, yValues: List<Entry>, label: String) : LineRadarDataSet<Entry>(yValues, label), ILineDataSet{
	private var mCircleColors: ArrayList<Int>? = null
	private var mCircleHoleColor = Color.WHITE
	private var mCircleRadius = 8F
	private var mCircleHoleRadius = 4F
	private var mCubicIntensity = 0.2F
	private var mDashPathEffect: DashPathEffect? = null
	private var mFillFormatter: IFillFormatter = DefaultFillFormatter()

	private var mDrawCircles = true
	private var mDrawCircleHole = true

	init {
		if(mCircleColors == null) {
			mCircleColors = ArrayList<Int>()
		}
		mCircleColors?.clear()
		mCircleColors!!.add(Color.rgb(140, 234, 255))
	}

	override fun copy(): DataSet<Entry> {
		val entries = ArrayList<Entry>()
		mValues.forEach { entries.add(it.copy()) }
		val copied = CustomLineDataSet(context, entries, label)
		copy(copied)

		return copied
	}

	fun copy(lineDataSet: CustomLineDataSet) {
		super.copy(lineDataSet)
		lineDataSet.mCircleColors = mCircleColors
		lineDataSet.mCircleHoleColor = mCircleHoleColor
		lineDataSet.mCircleHoleRadius = mCircleHoleRadius
		lineDataSet.mCircleRadius = mCircleRadius
		lineDataSet.mCubicIntensity = mCubicIntensity
		lineDataSet.mDashPathEffect = mDashPathEffect
		lineDataSet.mDrawCircleHole = mDrawCircleHole
		lineDataSet.mDrawCircles = mDrawCircleHole
		lineDataSet.mFillFormatter = mFillFormatter
	}

	override fun getMode(): LineDataSet.Mode {
		return LineDataSet.Mode.LINEAR
	}

	override fun getCubicIntensity(): Float {
		return mCubicIntensity
	}

	override fun isDrawCubicEnabled(): Boolean {
		return false
	}

	override fun isDrawSteppedEnabled(): Boolean {
		return false
	}

	override fun getCircleRadius(): Float {
		return mCircleRadius
	}

	override fun getCircleHoleRadius(): Float {
		return mCircleHoleRadius
	}

	override fun getCircleColor(index: Int): Int {
		return mCircleColors!![index]
	}

	override fun getCircleColorCount(): Int {
		return mCircleColors!!.size
	}

	override fun isDrawCirclesEnabled(): Boolean {
		return mDrawCircles
	}

	override fun getCircleHoleColor(): Int {
		return mCircleHoleColor
	}

	override fun isDrawCircleHoleEnabled(): Boolean {
		return mDrawCircleHole
	}

	override fun getDashPathEffect(): DashPathEffect? {
		return mDashPathEffect
	}

	override fun isDashedLineEnabled(): Boolean {
		return mDashPathEffect != null
	}

	override fun getFillFormatter(): IFillFormatter {
		return mFillFormatter
	}

	fun enableDashedLine(lineLength: Float, spaceLength: Float, phase: Float) {
		mDashPathEffect = DashPathEffect(floatArrayOf(lineLength, spaceLength), phase)
	}

	fun disableDashedLine() {
		mDashPathEffect = null
	}


	fun setCircleColor(color: Int) {
		resetCircleColors()
		mCircleColors?.add(color)
	}

	fun setCircleColors(colors: ArrayList<Int>, context: Context) {
		var clrs: ArrayList<Int> = mCircleColors!!
		if (clrs == null) {
			clrs = ArrayList()
		}
		clrs.clear()

		for (color in colors) {
			clrs.add(context.resources.getColor(color))
		}

		mCircleColors = clrs
	}

	fun resetCircleColors() {
		if(mCircleColors == null) {
			mCircleColors = ArrayList()
		}
		mCircleColors?.clear()
	}

	fun setCircleRadius(radius: Float) {
		if(radius >= 1F) {
			mCircleRadius = convertDpToPixel(radius)
		}

	}

	fun setCircleHoleColor(color: Int) {
		mCircleHoleColor = color
	}

	fun setCircleHoleRadius(holeRadius: Float) {
		if(holeRadius >= 0.5F) {
			mCircleHoleRadius = convertDpToPixel(holeRadius)
		}
	}

	fun setCircleSize(size: Float) {
		setCircleRadius(size)
	}

	fun setFillFormatter(formatter: IFillFormatter) {
		mFillFormatter = formatter ?: DefaultFillFormatter()
	}



	fun convertDpToPixel(dp : Float) : Float{
		val mMetrics = context.resources.displayMetrics ?: return dp
		return dp * mMetrics.density
	}
}