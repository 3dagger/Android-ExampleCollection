package com.dagger.custommarkerview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dagger.custommarkerview.di.Station
import com.orhanobut.logger.Logger
import com.pixplicity.easyprefs.library.Prefs
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val remoteService: RemoteService) : ViewModel() {
    private val _stationData = MutableLiveData<Station>()
    val stationData: LiveData<Station>
        get() = _stationData

    fun getMockStationList() {
        remoteService.apiMockStationList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy (
                onSuccess = {
                    _stationData.value = it.body()
                },
                onError = {

                }
            ).addTo(compositeDisposable = CompositeDisposable())
    }
}
