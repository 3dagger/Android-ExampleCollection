package com.dagger.navermapclustering.clustering.algorithm

import com.dagger.navermapclustering.clustering.Cluster
import com.dagger.navermapclustering.clustering.LeeamClusterItem

interface Algorithm<T: LeeamClusterItem> {

	val items: Collection<T>

	var maxDistanceBetweenClusteredItems: Int

	fun addItem(item: T)

	fun addItems(items: Collection<T>)

	fun clearItems()

	fun removeItem(item: T)

	fun getClusters(zoom: Double): Set<Cluster<T>>

}