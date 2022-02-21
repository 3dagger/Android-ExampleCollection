package com.dagger.navermapclustering.clustering.geometry

data class LeeamCameraPosition (
	var target: LeeamLatLng,
	var zoom: Double,
	var tilt: Double?,
	var bearing: Double?
)