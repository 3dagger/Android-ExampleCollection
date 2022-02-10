package com.dagger.realtimechart

import com.dagger.realtimechart.base.BaseViewModel

class MainViewModel(val remoteService: RemoteService) : BaseViewModel<MainNavigator.View>(), MainNavigator.VieWModel{


    override fun disposableClear() {
        onCleared()
    }
}