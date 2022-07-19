package com.dagger.navermapclustering.naver

import com.dagger.navermapclustering.R
import com.dagger.navermapclustering.clustering.LeeamMap
import com.dagger.navermapclustering.clustering.geometry.LeeamCameraPosition
import com.dagger.navermapclustering.clustering.geometry.LeeamLatLng
import com.dagger.navermapclustering.clustering.geometry.LeeamLatLngBounds
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.orhanobut.logger.Logger

class LeeamNaverMap(private val naverMap: NaverMap) : LeeamMap<Marker, LeeamNaverMarker, OverlayImage> {

	override fun getCameraPosition(): LeeamCameraPosition {
		val cameraPosition = naverMap.cameraPosition
		val leeamLatLng = LeeamLatLng(cameraPosition.target.latitude, cameraPosition.target.longitude)
		return LeeamCameraPosition(leeamLatLng, cameraPosition.zoom, cameraPosition.tilt, cameraPosition.bearing)
	}

	override fun addOnCameraIdleListener(onCameraIdleListener: (leeamCameraPosition: LeeamCameraPosition) -> Unit) {
		/*
        naverMap.addOnCameraChangeListener({ reason, animated ->
            Log.d("ted", "카메라 변경 - reson: $reason, animated: $animated")
            onCameraIdleListener.invoke(getCameraPosition())
        })
        */
		naverMap.addOnCameraIdleListener { onCameraIdleListener.invoke(getCameraPosition()) }
	}

	override fun addMarker(marker: LeeamNaverMarker) {
		marker.marker.map = naverMap
	}

	override fun removeMarker(marker: LeeamNaverMarker) {
		marker.marker.map = null
	}

	override fun getVisibleLatLngBounds(): LeeamLatLngBounds =
		LeeamLatLngBounds().apply {
			val bounds = naverMap.contentBounds

			southWest = LeeamLatLng(bounds.southWest.latitude, bounds.southWest.longitude)
			northEast = LeeamLatLng(bounds.northEast.latitude, bounds.northEast.longitude)
		}


	override fun moveToCenter(leeamLatLng: LeeamLatLng) {
		val cameraUpdate = CameraUpdate.scrollTo(LatLng(leeamLatLng.latitude, leeamLatLng.longitude))
			//.animate(CameraAnimation.Easing)
			.animate(CameraAnimation.Linear)
		naverMap.moveCamera(cameraUpdate)
	}

	override fun getMarker(): LeeamNaverMarker {
		return getMarker(Marker())
	}

	override fun getMarker(marker: Marker): LeeamNaverMarker {
		return LeeamNaverMarker(marker)
	}

	override fun addMarkerClickListener(marker: LeeamNaverMarker, action: (LeeamNaverMarker) -> Unit) {
		marker.marker.setOnClickListener {
			action.invoke(marker)
			true
		}
	}

}