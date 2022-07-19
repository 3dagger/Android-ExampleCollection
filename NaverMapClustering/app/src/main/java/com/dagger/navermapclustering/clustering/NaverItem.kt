package com.dagger.navermapclustering.clustering

import com.dagger.navermapclustering.clustering.geometry.LeeamLatLng
import com.naver.maps.geometry.LatLng


data class NaverItem(
	val position: LatLng,
	val idx: Int? = null,
	val socCount: Int? = null,
	val socMax: Double? = null,
	val isConnection: Boolean? = null,
	val stationId: String? = null,
	val stationDetailAddress: String? = null,
	val dealershipId: String? = null,
	val dealershipName: String? = null
) : LeeamClusterItem {
	override fun getLatLng(): LeeamLatLng {
		return LeeamLatLng(latitude = position.latitude, longitude = position.longitude)
	}

//	var title: String? = null
//	var snippet: String? = null
//
//	constructor(lat: Double, lng: Double) : this(LatLng(lat, lng)) {
//		title = null
//		snippet = null
//	}
//
//	constructor(lat: Double, lng: Double, title: String?, snippet: String?) : this(LatLng(lat, lng)) {
//		this.title = title
//		this.snippet = snippet
//	}
}
