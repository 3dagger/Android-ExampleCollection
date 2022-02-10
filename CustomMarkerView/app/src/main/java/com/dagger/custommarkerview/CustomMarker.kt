package com.dagger.custommarkerview

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible

class CustomMarker @JvmOverloads constructor(context: Context, private val type: MarkerType, countSoc: Int? = null, maxSoc: Double? = null): LinearLayout(context){

    private var marker          : ImageView
    private var tvSmallCount    : TextView
    private var tvLargeCount    : TextView

    init {
        val v = View.inflate(context, R.layout.custom_marker_small, this)
        marker = v.findViewById(R.id.marker)
        tvSmallCount = v.findViewById(R.id.tvBatterySmallCount)
        tvLargeCount = v.findViewById(R.id.tvBatteryLargeCount)


        when(type) {
            MarkerType.Small -> {
                setDrawSmallMarker(maxSoc = maxSoc)
                setDrawSmallCount(countSoc = countSoc)
            }
            MarkerType.Large -> {
                setDrawLargeMarker(maxSoc = maxSoc)
                setDrawLargeCount(countSoc = countSoc)
            }
            MarkerType.Error -> {
                setDrawUnavailableMarker()
                setDrawUnavailableCount()
            }
        }
    }

    private fun setDrawUnavailableMarker() {
        marker.setImageResource(R.drawable.s_station_pin_icon_error)
    }

    private fun setDrawUnavailableCount() {
        dismissLargeCount()
        dismissSmallCount()
    }

    private fun setDrawLargeMarker(maxSoc: Double?) {
        when {
            maxSoc!! >= 80.0    -> marker.setImageResource(R.drawable.l_station_pin_icon_g)
            maxSoc   >= 60.0    -> marker.setImageResource(R.drawable.l_station_pin_icon_y)
            else                -> marker.setImageResource(R.drawable.l_station_pin_icon_r)
        }
        onRefresh()
    }

    private fun setDrawSmallMarker(maxSoc: Double?) {
        maxSoc?.let {
            when {
                maxSoc  >= 80.0 -> marker.setImageResource(R.drawable.s_station_pin_icon_g)
                maxSoc  >= 60.0 -> marker.setImageResource(R.drawable.s_station_pin_icon_y)
                else            -> marker.setImageResource(R.drawable.s_station_pin_icon_r)
            }
        }
        onRefresh()
    }

    private fun setDrawSmallCount(countSoc: Int?) {
        tvSmallCount.text = if(countSoc == 0) "-" else countSoc.toString()
        showSmallCount()
        onRefresh()
    }

    private fun setDrawLargeCount(countSoc: Int?) {
        tvLargeCount.text = if(countSoc == 0) "-" else countSoc.toString()
        showLargeCount()
        onRefresh()
    }

    private fun showSmallCount() {
        if(!tvSmallCount.isVisible) tvSmallCount.visibility = View.VISIBLE
    }

    private fun dismissSmallCount() {
        if(tvSmallCount.isVisible) tvSmallCount.visibility = View.INVISIBLE
    }

    private fun showLargeCount() {
        if(!tvLargeCount.isVisible) tvLargeCount.visibility = View.VISIBLE
    }

    private fun dismissLargeCount() {
        if(tvLargeCount.isVisible) tvLargeCount.visibility = View.INVISIBLE
    }

    private fun onRefresh(){
        invalidate()
        requestLayout()
    }
}

enum class MarkerType {
    Small, Large, Error
}