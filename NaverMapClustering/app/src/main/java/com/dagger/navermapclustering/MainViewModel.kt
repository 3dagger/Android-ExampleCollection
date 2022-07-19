package com.dagger.navermapclustering

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dagger.navermapclustering.base.BaseViewModel
import com.dagger.navermapclustering.clustering.NaverItem
import com.naver.maps.geometry.LatLng
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(private val remoteService: RemoteService): BaseViewModel<MainNavigator.View>(), MainNavigator.ViewModel {
	private val _stationLocationData = MutableLiveData<List<StationList>>()
	val stationLocationData : LiveData<List<StationList>>
		get() = _stationLocationData

	private val _dealershipData = MutableLiveData<List<DealershipList>>()
	val dealershipData : LiveData<List<DealershipList>>
		get() = _dealershipData

	init {
		getMockStationList()
	}

	fun getMockStationList() {
		remoteService.apiMockStationList()
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribeBy(
				onSuccess = {
					_stationLocationData.value = it.body()
						?.stationList
						?.filter { stationList ->  stationList.lat != null && stationList.lon != null }
					_dealershipData.value = it.body()
						?.dealershipList
						?.filter { dealershipList ->  dealershipList.lat != null && dealershipList.lon != null }
				},
				onError = {

				}
			)
	}

	fun getProcessingStationMarker(data: List<StationList>?) : ArrayList<NaverItem> {
		val items = ArrayList<NaverItem>()
		data?.mapIndexed { idx, stationList ->
			items.add(NaverItem(position = LatLng(stationList.lat!!, stationList.lon!!), idx = idx, socCount = stationList.socCount, socMax = stationList.maxSoc, isConnection = stationList.isServiceable, stationId = stationList.stationId, stationDetailAddress = stationList.address2))
		}
		return items
	}

	fun getProcessingDealershipMarekr(data: List<DealershipList>?) : ArrayList<NaverItem> {
		val items = ArrayList<NaverItem>()
		data?.mapIndexed { idx, dealershipList ->
			items.add(NaverItem(position = LatLng(dealershipList.lat!!, dealershipList.lon!!), idx = idx, dealershipId = dealershipList.dealershipId, dealershipName = dealershipList.name))
		}
		return items
	}

	override fun disposableClear() {
		onCleared()
	}
}