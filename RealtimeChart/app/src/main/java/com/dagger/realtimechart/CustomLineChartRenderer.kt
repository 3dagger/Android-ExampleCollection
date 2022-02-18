package com.dagger.realtimechart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.animation.ChartAnimator
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider
import com.github.mikephil.charting.renderer.LineChartRenderer
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils
import com.github.mikephil.charting.utils.ViewPortHandler

class CustomLineChartRenderer constructor(private val context: Context, lineDataProvider: LineDataProvider, animator: ChartAnimator, viewPortHandler: ViewPortHandler) : LineChartRenderer(lineDataProvider, animator, viewPortHandler){

	override fun drawValues(c: Canvas?) {
		super.drawValues(c)
		if (isDrawingValuesAllowed(mChart)) {
			val dataSets = mChart.lineData.dataSets
			for (i in dataSets.indices) {
				val dataSet = dataSets[i]
				if (!shouldDrawValues(dataSet) || dataSet.entryCount < 1) continue

				applyValueTextStyle(dataSet)
				val trans = mChart.getTransformer(dataSet.axisDependency)

				var valOffset = (dataSet.circleRadius * 1.75f).toInt()
				if (!dataSet.isDrawCirclesEnabled) valOffset /= 2
				mXBounds[mChart] = dataSet
				val positions = trans.generateTransformedValuesLine(dataSet, mAnimator.phaseX, mAnimator.phaseY, mXBounds.min, mXBounds.max)
				val formatter = dataSet.valueFormatter
				val iconsOffset = MPPointF.getInstance(dataSet.iconsOffset)
				iconsOffset.x = Utils.convertDpToPixel(iconsOffset.x)
				iconsOffset.y = Utils.convertDpToPixel(iconsOffset.y)
				var j = 0
				while (j < positions.size) {
					val x = positions[j]
					val y = positions[j + 1]
					if (!mViewPortHandler.isInBoundsRight(x)) break
					if (!mViewPortHandler.isInBoundsLeft(x) || !mViewPortHandler.isInBoundsY(y)) {
						j += 2
						continue
					}
					val entry = dataSet.getEntryForIndex(j / 2 + mXBounds.min)

					/**
					 * @author : 이수현
					 * @Date : 2021/12/21 2:23 오후
					 * @Description : DrawValue 분기 처리
					 * @History :
					 *
					 **/
					if (dataSet.isDrawValuesEnabled) {
						if(j == positions.size - 2) {
							customDrawValues(c!!, formatter.getPointLabel(entry), x, y - valOffset, dataSet.getValueTextColor(j / 2))
						}else {
							drawValue(c!!, formatter.getPointLabel(entry), x, y - valOffset, dataSet.getValueTextColor(j / 2))
						}
					}

					if (entry.icon != null && dataSet.isDrawIconsEnabled) {
						val icon = entry.icon
						Utils.drawImage(c, icon, (x + iconsOffset.x).toInt(), (y + iconsOffset.y).toInt(), icon.intrinsicWidth, icon.intrinsicHeight)
					}
					j += 2
				}

				MPPointF.recycleInstance(iconsOffset)
			}
		}
	}
	override fun drawValue(c: Canvas, valueText: String, x: Float, y: Float, color: Int) {
		ContextCompat.getDrawable(context, R.drawable.chart_value_background)?.let { customDrawImage(c, it, x.toInt(), y.toInt() - 40, 55, 30, mValuePaint.measureText(context.convertComma(valueText.toInt())), valueText.length) }
		mValuePaint.color = color
		c.drawText(context.convertComma(valueText.toInt()).toString(), x, y - 20, mValuePaint)
	}
	/**
	 * @author : 이수현
	 * @Date : 2021/12/16 4:24 오후
	 * @Description : 마지막 아이템 수정 메서드(추가)
	 * @History :
	 *
	 **/
	private fun customDrawValues(c: Canvas, valueText: String, x: Float, y: Float, color: Int) {
		ContextCompat.getDrawable(context, R.drawable.chart_point_value_background)?.let { customDrawImage(c, it, x.toInt(), y.toInt() - 45, 55, 30, mValuePaint.measureText(context.convertComma(valueText.toInt())), valueText.length) }
		mValuePaint.color = color
		c.drawText(context.convertComma(valueText.toInt()).toString(), x, y - 25, mValuePaint)
	}

	/**
	 * @author : 이수현
	 * @Date : 2021/12/21 1:52 오후
	 * @Description : 커스텀 Value Background  ->  Value 자릿수에 따라 Left, Right Spread
	 * @History :
	 *
	 **/
	private val mDrawableBoundsCache = Rect()
	private fun customDrawImage(canvas: Canvas, drawable: Drawable, x: Int, y: Int, width: Int, height: Int, textWidth: Float, textLength: Int) {
		val drawOffset = MPPointF.getInstance()
		drawOffset.x = (x -(textWidth / 2))
		drawOffset.y = (y -(height / 2)).toFloat()
		drawable.copyBounds(mDrawableBoundsCache)

		val oneSpotDp =  Utility(context).convertDpToPixel(context, 6F)
		val dp = Utility(context).convertDpToPixel(context, 5F)

		if(textLength == 1) {
			drawable.setBounds(mDrawableBoundsCache.left - oneSpotDp, mDrawableBoundsCache.top, ((mDrawableBoundsCache.left + textWidth).toInt()) + oneSpotDp, mDrawableBoundsCache.top + width);
		}else {
			drawable.setBounds(mDrawableBoundsCache.left - dp, mDrawableBoundsCache.top, ((mDrawableBoundsCache.left + textWidth).toInt()) + dp, mDrawableBoundsCache.top + width);
		}

		val saveId = canvas.save()
		canvas.translate(drawOffset.x, drawOffset.y)
		drawable.draw(canvas)
		canvas.restoreToCount(saveId)
	}


}