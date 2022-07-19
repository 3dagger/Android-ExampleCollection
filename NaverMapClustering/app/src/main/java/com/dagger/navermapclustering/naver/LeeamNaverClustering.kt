package com.dagger.navermapclustering.naver

import android.content.Context
import com.dagger.navermapclustering.clustering.BaseBuilder
import com.dagger.navermapclustering.clustering.ClusterManager
import com.dagger.navermapclustering.clustering.LeeamClusterItem
import com.dagger.navermapclustering.clustering.LeeamClustering
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage

class LeeamNaverClustering<C : LeeamClusterItem>(clusterManager: ClusterManager<LeeamNaverClustering<C>, C, Marker, LeeamNaverMarker, NaverMap, OverlayImage>) :
	LeeamClustering<LeeamNaverClustering<C>, C, Marker, LeeamNaverMarker, NaverMap, OverlayImage>(clusterManager) {

	companion object {
		@JvmStatic
		fun <C : LeeamClusterItem> with(context: Context, map: NaverMap) = Builder<C>(context, map)
	}

		class Builder<C : LeeamClusterItem>(context: Context, map: NaverMap) :
			BaseBuilder<LeeamNaverClustering<C>, C, Marker, LeeamNaverMarker, NaverMap, OverlayImage>(context, LeeamNaverMap(map)) {
			override fun make(): LeeamNaverClustering<C> {
				return LeeamNaverClustering(ClusterManager(this))
			}
		}

}