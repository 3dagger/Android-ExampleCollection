package com.dagger.realtimechart

import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet

class DemoClass constructor(yValues: List<Entry>, label: String) : LineDataSet(yValues, label) {
	override fun setCircleRadius(radius: Float) {
		super.setCircleRadius(radius)
	}
}