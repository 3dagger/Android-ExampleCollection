package com.dagger.navermapclustering.clustering.algorithm

import com.dagger.navermapclustering.clustering.Cluster
import com.dagger.navermapclustering.clustering.LeeamClusterItem
import com.dagger.navermapclustering.clustering.geometry.LeeamCameraPosition

class ScreenBasedAlgorithmAdapter<T : LeeamClusterItem>(private val mAlgorithm: Algorithm<T>) : ScreenBasedAlgorithm<T> {
	override val items: Collection<T> get() = mAlgorithm.items
	override var maxDistanceBetweenClusteredItems: Int
		get() = mAlgorithm.maxDistanceBetweenClusteredItems
		set(maxDistance) {
			mAlgorithm.maxDistanceBetweenClusteredItems = maxDistance
		}
	override fun shouldReClusterOnMapMovement(): Boolean {
		return false
	}

	override fun addItem(item: T) {
		mAlgorithm.addItem(item)
	}

	override fun addItems(items: Collection<T>) {
		mAlgorithm.addItems(items)
	}

	override fun clearItems() {
		mAlgorithm.clearItems()
	}

	override fun removeItem(item: T) {
		mAlgorithm.removeItem(item)
	}

	override fun getClusters(zoom: Double): Set<Cluster<T>> {
		return mAlgorithm.getClusters(zoom)
	}

	override fun onCameraChange(leeamCameraPosition: LeeamCameraPosition) {
		// stub
	}


}