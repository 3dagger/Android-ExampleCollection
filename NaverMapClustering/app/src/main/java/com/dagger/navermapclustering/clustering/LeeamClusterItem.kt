package com.dagger.navermapclustering.clustering

import com.dagger.navermapclustering.clustering.geometry.LeeamLatLng

interface LeeamClusterItem {
	fun getLatLng() : LeeamLatLng
}