package com.dagger.navermapclustering.naver

import android.graphics.Bitmap
import com.dagger.navermapclustering.clustering.LeeamMarker
import com.dagger.navermapclustering.clustering.geometry.LeeamLatLng
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.orhanobut.logger.Logger

class LeeamNaverMarker(val marker: Marker) : LeeamMarker<OverlayImage> {

	override fun setVisible(visible: Boolean) {
		marker.isVisible = visible
	}

	override var position: LeeamLatLng
		get() = LeeamLatLng(marker.position.latitude, marker.position.longitude)
		set(value) {
			marker.position = LatLng(value.latitude, value.longitude)
		}

	override fun setImageDescriptor(imageDescriptor: OverlayImage) {
		marker.icon = imageDescriptor
	}

	override fun fromBitmap(bitmap: Bitmap): OverlayImage = OverlayImage.fromBitmap(bitmap)

}