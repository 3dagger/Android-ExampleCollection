package com.dagger.navermapclustering.clustering.algorithm

import com.dagger.navermapclustering.clustering.LeeamClusterItem
import com.dagger.navermapclustering.clustering.geometry.LeeamCameraPosition

interface ScreenBasedAlgorithm<T : LeeamClusterItem> : Algorithm<T> {

	fun shouldReClusterOnMapMovement(): Boolean

	fun onCameraChange(leeamCameraPosition: LeeamCameraPosition)
}