package com.dagger.navermapclustering.clustering

import com.dagger.navermapclustering.clustering.geometry.LeeamLatLng
import com.naver.maps.geometry.LatLng

data class NaverItem(var position: LatLng) : LeeamClusterItem {
	override fun getLatLng(): LeeamLatLng {
		return LeeamLatLng(
			longitude = position.latitude,
			latitude = position.longitude
		)
	}

	var title: String? = null
	var snippet: String? = null

	constructor(lat: Double, lng: Double) : this(LatLng(lat, lng)) {
		title = null
		snippet = null
	}

	constructor(lat: Double, lng: Double, title: String?, snippet: String?) : this(LatLng(lat, lng)) {
		this.title = title
		this.snippet = snippet
	}
}
