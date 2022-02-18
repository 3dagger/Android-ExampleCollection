package com.dagger.realtimechart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.renderer.XAxisRenderer
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Transformer
import com.github.mikephil.charting.utils.Utils
import com.github.mikephil.charting.utils.ViewPortHandler

class CustomXAxisRenderer(viewPortHandler: ViewPortHandler?, private var mXAxis: XAxis, trans: Transformer?, private val context: Context) : XAxisRenderer(viewPortHandler, mXAxis, trans) {

	override fun drawLabels(c: Canvas?, pos: Float, anchor: MPPointF?) {
		super.drawLabels(c, pos, anchor)
		val labelRotationAngleDegrees = mXAxis.labelRotationAngle
		val centeringEnabled = mXAxis.isCenterAxisLabelsEnabled
		val positions = FloatArray(mXAxis.mEntryCount * 2)
		run {
			var i = 0
			while (i < positions.size) {
				if (centeringEnabled) {
					positions[i] = mXAxis.mCenteredEntries[i / 2]
				} else {
					positions[i] = mXAxis.mEntries[i / 2]
				}
				i += 2
			}
		}
		mTrans.pointValuesToPixel(positions)
		var i = 0
		while (i < positions.size) {
			var x = positions[i]
			if (mViewPortHandler.isInBoundsX(x)) {
				val label = mXAxis.valueFormatter.getAxisLabel(mXAxis.mEntries[i / 2], mXAxis)

				if (mXAxis.isAvoidFirstLastClippingEnabled) {

					// avoid clipping of the last
					if (i / 2 == mXAxis.mEntryCount - 1 && mXAxis.mEntryCount > 1) {
						val width = Utils.calcTextWidth(mAxisLabelPaint, label).toFloat()
						if (width > mViewPortHandler.offsetRight() * 2
							&& x + width > mViewPortHandler.chartWidth
						) x -= width / 2

						// avoid clipping of the first
					} else if (i == 0) {
						val width = Utils.calcTextWidth(mAxisLabelPaint, label).toFloat()
						x += width / 2
					}
				}

				/**
				 * @author : 이수현
				 * @Date : 2021/12/16 10:52 오전
				 * @Description : 포지션 - 2 인 경우 (마지막 값) 컬러 변경
				 * @History :
				 *
				 **/
				if (i == positions.size - 2) {
					mAxisLabelPaint.apply {
						color = Color.parseColor("#3c3c3c")
//                        typeface = ResourcesCompat.getFont(context, R.font.notosanskrbold)
					}
				}
				drawLabel(c, label, x, pos, anchor, labelRotationAngleDegrees)
			}
			i += 2
		}
	}
}