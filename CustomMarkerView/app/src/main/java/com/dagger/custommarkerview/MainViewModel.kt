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


//        remoteService.apiMockStationList(
//            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyTWdwbVozTzRocTVmOTMtMGJ5YUxiOTNHVkpYOVVKek80UjFtRElpTk9vIiwicm9sZXMiOiJDTElFTlQiLCJleHAiOjE2NzQ3MTc1NjYsImlzcyI6Ik5hbnVBUEkiLCJhdWQiOiJodHRwczovL2FwaS1uYW51LmFpbXMtcm5kLmNvbSJ9.n2CPpSi0s9V727iXm-AJBwi7ZK9v2yAspEUAHOj5c7c",
//            apiVersion = "2",
//            filter = EntiretyStationReq(lat = null, lon = null, distance = null, soc = null)
//        )
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeBy (
//                onSuccess = {
//                    _stationData.value = it.body()
//                },
//                onError = {
//
//                }
//            ).addTo(compositeDisposable = CompositeDisposable())
    }
}
