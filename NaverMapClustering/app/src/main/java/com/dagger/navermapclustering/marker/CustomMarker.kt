package com.dagger.navermapclustering.marker

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.dagger.navermapclustering.MarkerType
import com.dagger.navermapclustering.R

enum class MarkerType {
	Small, Large, Error
}

class CustomStationMarker @JvmOverloads constructor(context: Context, type: MarkerType, countSoc: Int? = null, maxSoc: Double? = null): LinearLayout(context) {
	private var marker          : ImageView
	private var tvSmallCount    : TextView
	private var tvLargeCount    : TextView

	/**
	 * @author : 이수현
	 * @Date : 2022/03/30 11:01 오전
	 * @Description :   해당 클래스 초기화 메서드
	 *                  - 마커 타입에 따라 해당하는 아이콘 및 텍스트 설정
	 * @History :
	 *
	 **/
	init {
		val v = View.inflate(context, R.layout.custom_station_marker, this)
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

	/**
	 * @author : 이수현
	 * @Date : 2022/03/30 10:23 오전
	 * @Description : 스테이션이 이용불가 상태일때 이용불가 아이콘 설정 메서드
	 * @History :
	 *
	 **/
	private fun setDrawUnavailableMarker() {
		marker.setImageResource(R.drawable.s_station_pin_icon_error)
	}

	/**
	 * @author : 이수현
	 * @Date : 2022/03/30 10:40 오전
	 * @Description : 스테이션이 이용불가 상태일 때 기본,확대 텍스트뷰 Invisible 메서드
	 * @History :
	 *
	 **/
	private fun setDrawUnavailableCount() {
		dismissLargeCount()
		dismissSmallCount()
	}

	/**
	 * @author : 이수현
	 * @Date : 2022/03/30 10:41 오전
	 * @Description :   확대 사이즈 마커 생성 메서드
	 *                  - maxSoc 에 따라 Value 부분이 비어있는 이미지 Set
	 *                  - View Refresh 메서드 호출
	 * @History :
	 *
	 **/
	private fun setDrawLargeMarker(maxSoc: Double?) {
		when {
			maxSoc!! >= 80.0    -> marker.setImageResource(R.drawable.l_station_pin_icon_g)
			maxSoc   >= 60.0    -> marker.setImageResource(R.drawable.l_station_pin_icon_y)
			else                -> marker.setImageResource(R.drawable.l_station_pin_icon_r)
		}
		onRefresh()
	}

	/**
	 * @author : 이수현
	 * @Date : 2022/03/30 10:55 오전
	 * @Description :   기본 사이즈 마커 생성 메서드
	 *                  - maxSoc 에 따라 Value 부분이 비어있는 이미지 Set
	 *                  - View Refresh 메서드 호출
	 * @History :
	 *
	 **/
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

	/**
	 * @author : 이수현
	 * @Date : 2022/03/30 10:50 오전
	 * @Description :   기본 마커에 대한 텍스트뷰 생성 메서드
	 *                  - 해당 텍스트뷰에 대한 Value 0 이면 "-" 아니면 "Value.toString()" 으로 출력
	 *                  - 기본 마커에 해당하는 텍스트뷰 Visible 메서드 호출
	 *                  - View Refresh 메서드 호출
	 * @History :
	 *
	 **/
	private fun setDrawSmallCount(countSoc: Int?) {
		tvSmallCount.text = if(countSoc == 0) "-" else countSoc.toString()
		showSmallCount()
		onRefresh()
	}

	/**
	 * @author : 이수현
	 * @Date : 2022/03/30 10:50 오전
	 * @Description :   확대 마커에 대한 텍스트뷰 생성 메서드
	 *                  - 해당 텍스트뷰에 대한 Value 0 이면 "-" 아니면 "Value.toString()"으로 출력
	 *                  - 확대 마커에 해당하는 텍스트뷰 Visible 메서드 호출
	 *                  - View Refresh 메서드 호출
	 * @History :
	 *
	 **/
	private fun setDrawLargeCount(countSoc: Int?) {
		tvLargeCount.text = if(countSoc == 0) "-" else countSoc.toString()
		showLargeCount()
		onRefresh()
	}

	/**
	 * @author : 이수현
	 * @Date : 2022/03/30 10:48 오전
	 * @Description : 마커 아이콘 축소시 해당 텍스트뷰 Visible 메서드
	 * @History :
	 *
	 **/
	private fun showSmallCount() {
		if(!tvSmallCount.isVisible) tvSmallCount.visibility = View.VISIBLE
	}

	/**
	 * @author : 이수현
	 * @Date : 2022/03/30 10:46 오전
	 * @Description : 마커 아이콘 확대시 해당 텍스트뷰 Invisible 메서드
	 * @History :
	 *
	 **/
	private fun dismissSmallCount() {
		if(tvSmallCount.isVisible) tvSmallCount.visibility = View.INVISIBLE
	}

	/**
	 * @author : 이수현
	 * @Date : 2022/03/30 10:43 오전
	 * @Description : 마커 아이콘 축소시 해당하는 테그스트뷰 Invisible 메서드
	 * @History :
	 *
	 **/
	private fun dismissLargeCount() {
		if(tvLargeCount.isVisible) tvLargeCount.visibility = View.INVISIBLE
	}

	/**
	 * @author : 이수현
	 * @Date : 2022/03/30 10:43 오전
	 * @Description : 마커 아이콘 확대시 해당하는 텍스트뷰 Visible 메서드
	 * @History :
	 *
	 **/
	private fun showLargeCount() {
		if(!tvLargeCount.isVisible) tvLargeCount.visibility = View.VISIBLE
	}

	/**
	 * @author : 이수현
	 * @Date : 2022/03/30 10:44 오전
	 * @Description : View Refresh 메서드
	 * @History :
	 *
	 **/
	private fun onRefresh(){
		invalidate()
		requestLayout()
	}
}