package com.dagger.navermapclustering

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.dagger.navermapclustering.base.BaseActivity
import com.dagger.navermapclustering.clustering.NaverItem
import com.dagger.navermapclustering.clustering.quadtree.PointQuadTree
import com.dagger.navermapclustering.databinding.ActivityMainBinding
import com.dagger.navermapclustering.marker.CustomStationMarker
import com.dagger.navermapclustering.naver.LeeamNaverClustering
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.orhanobut.logger.Logger
import org.koin.android.ext.android.inject

data class DealershipMarkerStatus(var type: MarkerType, val dealershipId: String)
data class StationMarkerStatus(var type: MarkerType, var batteryCount: Int?, var maxSoc: Double?, var isConnected: Boolean, var stationId: String?)

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator.View, OnMapReadyCallback{
	override val viewModel: MainViewModel by inject()
	override val layoutResourceId: Int get() = R.layout.activity_main
	lateinit var naverMap: NaverMap

	private lateinit var naverMarkerArray : ArrayList<NaverItem>

	private lateinit var dealershipMarkerArray : ArrayList<Pair<DealershipMarkerStatus, Marker>>
	private lateinit var markerArray : ArrayList<Pair<StationMarkerStatus, Marker>>


	override fun initView(savedInstanceState: Bundle?) {
		viewModel.setNavigator(this@MainActivity)
	}

	override fun onProcess() {
		initViewSetup()
		subscribeObservers()

		markerArray = ArrayList()
		dealershipMarkerArray = ArrayList()
		naverMarkerArray = ArrayList()
	}

	private fun subscribeObservers() {

	}

	private fun initViewSetup() {
		val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as MapFragment?
			?: run {
				MapFragment.newInstance().also {
					supportFragmentManager.beginTransaction().add(R.id.map, it).commit()
				}
			}
		mapFragment.getMapAsync(this)
	}


	override fun onMapReady(nMap: NaverMap) {
		this.naverMap = nMap

		naverMap.moveCamera(
			CameraUpdate.toCameraPosition(CameraPosition(NaverMap.DEFAULT_CAMERA_POSITION.target, NaverMap.DEFAULT_CAMERA_POSITION.zoom))
		)

		nMap.moveCamera(CameraUpdate.toCameraPosition(CameraPosition(LatLng(37.48170564290578, 127.12282988647213), 8.0, 0.0, 0.0)).animate(CameraAnimation.Fly, 1500))

		viewModel.stationLocationData.observe(this) {
			LeeamNaverClustering.with<NaverItem>(this, nMap)
				.customMarker {
					Marker(it.position).apply {
						icon = when(it.isConnection!!) {
							true -> OverlayImage.fromView(CustomStationMarker(context = this@MainActivity, type = MarkerType.Small, countSoc = it.socCount, maxSoc = it.socMax))
							false -> OverlayImage.fromView(CustomStationMarker(context = this@MainActivity, type = MarkerType.Error, countSoc = it.socCount, maxSoc = it.socMax))
						}
					}
				}
				.markerClickListener {
					Toast.makeText(this, "${it.position}", Toast.LENGTH_SHORT).show()
				}
				.clusterClickListener {
					nMap.moveCamera(CameraUpdate.toCameraPosition(CameraPosition(LatLng(it.position.latitude, it.position.longitude), nMap.cameraPosition.zoom + 1, 0.0, 0.0)).animate(CameraAnimation.Fly, 500))
				}
				.items(viewModel.getProcessingStationMarker(it))
				.make()
		}

		viewModel.dealershipData.observe(this) {
			LeeamNaverClustering.with<NaverItem>(this, nMap)
				.customMarker {
					Marker(it.position).apply {
						icon = OverlayImage.fromResource(R.drawable.s_70_dealership_pin_icon)
					}
				}
				.clusterBackground { Color.RED }
				.clusterClickListener {
					nMap.moveCamera(CameraUpdate.toCameraPosition(CameraPosition(LatLng(it.position.latitude, it.position.longitude), nMap.cameraPosition.zoom + 1, 0.0, 0.0)).animate(CameraAnimation.Fly, 500))
				}

				.items(viewModel.getProcessingDealershipMarekr(it))
				.make()
		}

	}

	fun drawStationMarker(nMap: NaverMap, ) {

	}

	override fun onViewModelCleared() {
		viewModel.disposableClear()
	}
}
