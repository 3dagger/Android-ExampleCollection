package com.dagger.navermapclustering.clustering

import android.graphics.Bitmap
import com.dagger.navermapclustering.clustering.geometry.LeeamLatLng

interface LeeamMarker<ImageDescriptor> {

	fun serVisible(visible: Boolean)

	var position: LeeamLatLng

	fun setImageDescriptor(imageDescriptor: ImageDescriptor)

	fun fromBitmap(bitmap: Bitmap): ImageDescriptor
}