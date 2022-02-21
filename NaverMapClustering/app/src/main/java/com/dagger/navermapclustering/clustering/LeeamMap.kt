package com.dagger.navermapclustering.clustering

import com.dagger.navermapclustering.clustering.geometry.LeeamCameraPosition
import com.dagger.navermapclustering.clustering.geometry.LeeamLatLng
import com.dagger.navermapclustering.clustering.geometry.LeeamLatLngBounds

interface LeeamMap<RealMarker, LM : LeeamMarker<ImageDescriptor>, ImageDescriptor> {
	fun getCameraPosition() : LeeamCameraPosition

	fun addOnCameraIdleListener(onCameraIdleListener: ((leeamCameraPosition: LeeamCameraPosition) -> Unit))

	fun addMarker(marker: LM)

	fun removeMarker(marker: LM)

	fun getVisibleLatLngBounds(): LeeamLatLngBounds

	fun moveToCenter(leeamLatLng: LeeamLatLng)

	fun getMarker(): LM

	fun getMarker(marker: RealMarker): LM

	fun addMarkerClickListener(marker: LM, action: ((LM) -> Unit))
}