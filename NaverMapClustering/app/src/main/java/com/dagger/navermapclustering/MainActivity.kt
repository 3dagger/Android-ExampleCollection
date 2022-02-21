package com.dagger.navermapclustering

import android.os.Bundle
import com.dagger.navermapclustering.base.BaseActivity
import com.dagger.navermapclustering.clustering.NaverItem
import com.dagger.navermapclustering.databinding.ActivityMainBinding
import com.naver.maps.map.*
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator.View, OnMapReadyCallback{
	override val viewModel: MainViewModel by inject()
	override val layoutResourceId: Int get() = R.layout.activity_main
	lateinit var naverMap: NaverMap

	override fun initView(savedInstanceState: Bundle?) {
		viewModel.setNavigator(this@MainActivity)
	}

	override fun onProcess() {
		val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as MapFragment?
			?: run {
				MapFragment.newInstance().also {
					supportFragmentManager.beginTransaction().add(R.id.map, it).commit()
				}
			}
		mapFragment.getMapAsync(this)
	}

	override fun onViewModelCleared() {
		viewModel.disposableClear()
	}

	override fun onMapReady(nMap: NaverMap) {
		this.naverMap = nMap
		naverMap.moveCamera(
			CameraUpdate.toCameraPosition(CameraPosition(NaverMap.DEFAULT_CAMERA_POSITION.target, NaverMap.DEFAULT_CAMERA_POSITION.zoom))
		)
	}

	fun getItems(): List<NaverItem> {
		val bounds = naverMap.contentBounds
		return ArrayList<NaverItem>().apply {
			val temp = NaverItem(
				(bounds.northLatitude - bounds.southLatitude) * Math.random() + bounds.southLatitude,
				(bounds.eastLongitude - bounds.westLongitude) * Math.random() + bounds.westLongitude
			)
			add(temp)
		}
	}


}