package com.dagger.navermapclustering.clustering

import com.dagger.navermapclustering.clustering.algorithm.ScreenBasedAlgorithm

abstract class LeeamClustering<Clustering, C: LeeamClusterItem, RealMarker, Marker : LeeamMarker<ImageDescriptor>, Map, ImageDescriptor>(
	private val clusterManager: ClusterManager<Clustering, C, RealMarker, Marker, Map, ImageDescriptor>
) {
	fun clearItems() = internalChangeItem { clusterManager.clearItems() }

	fun addItems(items: Collection<C>) = internalChangeItem { clusterManager.addItems(items) }

	fun addItem(myItem: C) = internalChangeItem { clusterManager.addItem(myItem) }

	fun removeItem(item: C) = internalChangeItem { clusterManager.removeItem(item) }


	private fun internalChangeItem(action: (() -> Unit)) {
		action.invoke()
		clusterManager.cluster()
	}

	fun setAlgorithm(algorithm: ScreenBasedAlgorithm<C>) = clusterManager.setAlgorithm(algorithm)

}