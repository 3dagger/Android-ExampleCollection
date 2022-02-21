package com.dagger.navermapclustering

import com.dagger.navermapclustering.base.BaseViewModel

class MainViewModel(private val remoteService: RemoteService): BaseViewModel<MainNavigator.View>(), MainNavigator.ViewModel {
	override fun disposableClear() {
		onCleared()
	}
}