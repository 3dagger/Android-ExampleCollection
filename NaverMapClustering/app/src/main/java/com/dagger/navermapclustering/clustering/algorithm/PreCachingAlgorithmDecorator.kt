package com.dagger.navermapclustering.clustering.algorithm

import androidx.collection.LruCache
import com.dagger.navermapclustering.clustering.Cluster
import com.dagger.navermapclustering.clustering.LeeamClusterItem
import java.util.concurrent.locks.ReentrantReadWriteLock

class PreCachingAlgorithmDecorator<T : LeeamClusterItem>(private val mAlgorithm: Algorithm<T>) : Algorithm<T> {

	private val mCache = LruCache<Int, Set<Cluster<T>>>(5)
	private val mCacheLock = ReentrantReadWriteLock()

	override val items: Collection<T>
		get() = mAlgorithm.items

	override var maxDistanceBetweenClusteredItems: Int
		get() = mAlgorithm.maxDistanceBetweenClusteredItems
		set(maxDistance) {
			mAlgorithm.maxDistanceBetweenClusteredItems = maxDistance
			clearCache()
		}

	override fun addItem(item: T) {
		mAlgorithm.addItem(item)
		clearCache()
	}

	override fun addItems(items: Collection<T>) {
		mAlgorithm.addItems(items)
		clearCache()
	}

	override fun clearItems() {
		mAlgorithm.clearItems()
		clearCache()
	}

	override fun removeItem(item: T) {
		mAlgorithm.removeItem(item)
		clearCache()
	}

	private fun clearCache() {
		mCache.evictAll()
	}

	override fun getClusters(zoom: Double): Set<Cluster<T>> {
		val discreteZoom = zoom.toInt()
		val results = getClustersInternal(discreteZoom)

		if (mCache.get(discreteZoom + 1) == null) {
			Thread(PrecacheRunnable(discreteZoom + 1)).start()
		}
		if (mCache.get(discreteZoom - 1) == null) {
			Thread(PrecacheRunnable(discreteZoom - 1)).start()
		}
		return results
	}

	private fun getClustersInternal(discreteZoom: Int): Set<Cluster<T>> {
		var results: Set<Cluster<T>>?
		mCacheLock.readLock().lock()
		results = mCache.get(discreteZoom)
		mCacheLock.readLock().unlock()

		if (results == null) {
			mCacheLock.writeLock().lock()
			results = mCache.get(discreteZoom)
			if (results == null) {
				results = mAlgorithm.getClusters(discreteZoom.toDouble())
				mCache.put(discreteZoom, results)
			}
			mCacheLock.writeLock().unlock()
		}
		return results
	}

	private inner class PrecacheRunnable(private val mZoom: Int) : Runnable {

		override fun run() {
			try {
				// Wait between 500 - 1000 ms.
				Thread.sleep((Math.random() * 500 + 500).toLong())
			} catch (e: InterruptedException) {
				// ignore. keep going.
			}

			getClustersInternal(mZoom)
		}
	}
}
