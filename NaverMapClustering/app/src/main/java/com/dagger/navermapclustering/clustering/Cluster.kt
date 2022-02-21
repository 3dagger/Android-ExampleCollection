package com.dagger.navermapclustering.clustering

import com.dagger.navermapclustering.clustering.geometry.LeeamLatLng

interface Cluster<T : LeeamClusterItem> {
	val position: LeeamLatLng

	val items: Collection<T>

	val size: Int
}