package com.dagger.navermapclustering.clustering

import com.orhanobut.logger.Logger

class MarkerManager<RealMarker, Marker : LeeamMarker<ImageDescriptor>, ImageDescriptor>(private val leeamMap: LeeamMap<RealMarker, Marker, ImageDescriptor>) {
	private val allMarkerMap = HashMap<Marker, MarkerCollection>()

	fun newCollection(): MarkerCollection = MarkerCollection()

	fun remove(marker: Marker) {
		allMarkerMap[marker]?.remove(marker)
	}

	fun onMarkerClick(marker: Marker) {
		val markerCollection = allMarkerMap[marker]
		markerCollection?.markerClickListener?.invoke(marker)
	}

	inner class MarkerCollection {
		private val mMarkers = HashSet<Marker>()
		var markerClickListener: ((Marker) -> Unit)? = null

		fun addMarker(marker: Marker): Marker {
			leeamMap.addMarker(marker)
			mMarkers.add(marker)
			allMarkerMap[marker] = this@MarkerCollection
			return marker
		}

		fun addAll(collection: Collection<Marker>) {
			for(marker in collection) {
				addMarker(marker)
			}
		}

		fun addAll(collection: Collection<Marker>, defaultVisible: Boolean) {
			for (marker in collection) {
				addMarker(marker).setVisible(defaultVisible)
			}
		}

		fun showAll() {
			for (marker in mMarkers) {
				marker.setVisible(true)
			}
		}

		fun hideAll() {
			for (marker in mMarkers) {
				marker.setVisible(false)
			}
		}

		fun remove(leeamMarker: Marker): Boolean {
			if (mMarkers.remove(leeamMarker)) {
				allMarkerMap.remove(leeamMarker)
				leeamMap.removeMarker(leeamMarker)
				return true
			}
			return false
		}

		fun clear() {
			for (marker in mMarkers) {
				leeamMap.removeMarker(marker)
				allMarkerMap.remove(marker)
			}
			mMarkers.clear()
		}

		fun setOnMarkerClickListener(markerClickListener: ((Marker) -> Unit)) {
			this.markerClickListener = markerClickListener
		}

	}
}