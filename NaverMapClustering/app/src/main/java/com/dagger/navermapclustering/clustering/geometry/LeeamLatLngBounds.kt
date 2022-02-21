package com.dagger.navermapclustering.clustering.geometry

data class LeeamLatLngBounds(
	var southWest: LeeamLatLng = LeeamLatLng(0.0, 0.0),
	var northEast: LeeamLatLng = LeeamLatLng(0.0, 0.0)
) {
	constructor(
		northLatitude: Double,
		eastLongitude: Double,
		southLatitude: Double,
		westLongitude: Double
	) : this(LeeamLatLng(southLatitude, westLongitude), LeeamLatLng(northLatitude, eastLongitude))
	
	operator fun contains(leeamLatLng: LeeamLatLng): Boolean {
		return containLatLng(leeamLatLng.latitude, southWest.latitude, northEast.latitude) 
				&& containLatLng(leeamLatLng.longitude, southWest.longitude, northEast.longitude)
	}
	
	private fun containLatLng(target: Double, value1: Double, value2: Double): Boolean {
		return if(value1 < value2) {
			target in value1..value2
		} else {
			target in value2..value1
		}
	}
}
