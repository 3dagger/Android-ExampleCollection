package com.dagger.navermapclustering.clustering

import android.os.AsyncTask
import android.os.Build
import android.util.Log
import com.dagger.navermapclustering.clustering.algorithm.*
import com.dagger.navermapclustering.clustering.geometry.LeeamCameraPosition
import com.dagger.navermapclustering.clustering.view.ClusterRenderer
import java.util.concurrent.locks.ReentrantReadWriteLock

class ClusterManager<Clustering, C : LeeamClusterItem, RealMarker, Marker : LeeamMarker<ImageDescriptor>, Map, ImageDescriptor>(
	builder: BaseBuilder<Clustering, C, RealMarker, Marker, Map, ImageDescriptor>
) {
	val map: LeeamMap<RealMarker, Marker, ImageDescriptor> = builder.map

	val markerManager: MarkerManager<RealMarker, Marker, ImageDescriptor> = MarkerManager(map)

	val markerMarkerCollection: MarkerManager<RealMarker, Marker, ImageDescriptor>.MarkerCollection = markerManager.newCollection()

	val clusterMarkerMarkerCollection: MarkerManager<RealMarker, Marker, ImageDescriptor>.MarkerCollection = markerManager.newCollection()

	private var mAlgorithm: ScreenBasedAlgorithm<C> = ScreenBasedAlgorithmAdapter(
		PreCachingAlgorithmDecorator(NonHierarchicalDistanceBasedAlgorithm())
	)
	private val mAlgorithmLock = ReentrantReadWriteLock()

	private var mRenderer: ClusterRenderer<Clustering, C, RealMarker, Marker, Map, ImageDescriptor>

	private var previousCameraPosition: LeeamCameraPosition? = null

	private var mClusterTask: ClusterTask = ClusterTask()

	var algorithm: Algorithm<C>?
		get() = mAlgorithm
		set(algorithm) = if (algorithm is ScreenBasedAlgorithm) {
			setAlgorithm(algorithm)
		} else {
			setAlgorithm(ScreenBasedAlgorithmAdapter(algorithm!!))
		}

	init {
		map.addOnCameraIdleListener(::onCameraIdle)
		mRenderer = ClusterRenderer(builder, this)
		builder.item?.let { addItem(it) }
		builder.items?.let { addItems(it) }
	}

	internal fun onMarkerClick(marker: Marker) {
		markerManager.onMarkerClick(marker)
	}

	fun setAlgorithm(algorithm: ScreenBasedAlgorithm<C>) {
		mAlgorithmLock.writeLock().lock()
		try {
			algorithm.addItems(mAlgorithm.items)

			mAlgorithm = algorithm
		} finally {
			mAlgorithmLock.writeLock().unlock()
		}

		if (mAlgorithm.shouldReClusterOnMapMovement()) {
			mAlgorithm.onCameraChange(map.getCameraPosition())
		}

		cluster()
	}

	private fun internalLockSafe(action: (() -> Unit)) {
		mAlgorithmLock.writeLock().lock()
		try {
			action.invoke()
		} catch (e: Exception) {
			Log.e("ted", e.localizedMessage)
		} finally {
			mAlgorithmLock.writeLock().unlock()
		}
	}

	fun clearItems() {
		internalLockSafe {
			mAlgorithm.clearItems()
		}
	}

	fun addItems(items: Collection<C>) {
		internalLockSafe {
			mAlgorithm.addItems(items)
		}
	}


	fun addItem(myItem: C) {
		internalLockSafe {
			mAlgorithm.addItem(myItem)
		}
	}

	fun removeItem(item: C) {
		internalLockSafe {
			mAlgorithm.removeItem(item)
		}
	}

	/**
	 * Force a re-cluster. You may want to call this after adding new item(s).
	 */
	fun cluster() {
		internalLockSafe {
			// Attempt to cancel the in-flight request.
			mClusterTask.cancel(true)
			mClusterTask = ClusterTask()
			val zoom = map.getCameraPosition().zoom
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
				mClusterTask.execute(zoom)
			} else {
				mClusterTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, zoom)
			}
		}

	}

	private fun onCameraIdle(cameraPosition: LeeamCameraPosition) {

		mAlgorithm.onCameraChange(cameraPosition)

		// delegate clustering to the algorithm
		if (mAlgorithm.shouldReClusterOnMapMovement()) {
			cluster()

			// Don't re-compute clusters if the map has just been panned/tilted/rotated.
		} else if (!isSameZoom(cameraPosition)) {
			previousCameraPosition = cameraPosition
			cluster()
		}
	}

	private fun isSameZoom(cameraPosition: LeeamCameraPosition) = previousCameraPosition?.zoom == cameraPosition.zoom

	/**
	 * Runs the clustering algorithm in a background thread, then re-paints when results come back.
	 */
	private inner class ClusterTask : AsyncTask<Double, Void, Set<Cluster<C>>>() {
		override fun doInBackground(vararg zoom: Double?): Set<Cluster<C>> {
			mAlgorithmLock.readLock().lock()
			try {
				return mAlgorithm.getClusters(zoom[0]!!)
			} finally {
				mAlgorithmLock.readLock().unlock()
			}
		}


		override fun onPostExecute(clusters: Set<Cluster<C>>) {
			mRenderer.onClustersChanged(clusters)
		}
	}

}
