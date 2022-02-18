package com.dagger.realtimechart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dagger.realtimechart.base.BaseViewModel
import com.github.mikephil.charting.data.Entry
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class MainViewModel(val remoteService: RemoteService) : BaseViewModel<MainNavigator.View>(), MainNavigator.VieWModel{
    private val _mockData = MutableLiveData<FakeModel>()
    val mockData : LiveData<FakeModel>
        get() = _mockData

    private val _batteryUsageEntryData = MutableLiveData<LinkedList<Entry>>()
    val batteryUsageEntryData : LiveData<LinkedList<Entry>>
        get() = _batteryUsageEntryData

    private val _moveLast = MutableLiveData<Boolean>()
    val moveLast : LiveData<Boolean>
        get() = _moveLast

    private val _isEmptyData = MutableLiveData<Boolean>()
    val isEmptyData : LiveData<Boolean>
        get() = _isEmptyData

    var lastEntryData                   = ArrayList<Entry>()
    var xAxisValue                      = LinkedList<String>()
    var idx = 0

    private val entryData = LinkedList<Entry>()

    init {
        fetchData(period = "day", page = 0, isFirst = true)
//        fetchData(period = "month", page = 0, isFirst = true)
    }

    fun fetchData(period: String, page: Int, isFirst: Boolean) {
        addDisposable(remoteService.apiGetDailyRental(page = page, period = period,token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyTWdwbVozTzRocTVmOTMtMGJ5YUxiOTNHVkpYOVVKek80UjFtRElpTk9vIiwicm9sZXMiOiJDTElFTlQiLCJleHAiOjE2NzYzNDAwNTcsImlzcyI6Ik5hbnVBUEkiLCJhdWQiOiJodHRwczovL2FwaS1uYW51LmFpbXMtcm5kLmNvbSJ9.py3Gd4-E_WSsq9l8mMWZzzrhq5RNfnT4Q7LCi_Zz63c")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showProgress() }
            .doOnTerminate { dismissProgress() }
            .subscribeBy(
                onSuccess = {
                    it.body()?.let { data ->
                        when (data.rentReport.socReportArray.isNullOrEmpty()) {
                            true -> setEmptyData(isEmpty = true)
                            false -> if (isFirst) processingInitData(period, data) else processingAddData(period, data)
                        }
                    }
//                    Logger.d("it.body :: ${it.body()}")
                },
                onError = {}
            )
        )
    }

    fun setMoveLast(isLast: Boolean) {
        _moveLast.value = isLast
    }

    fun setEmptyData(isEmpty: Boolean) {
        _isEmptyData.value = isEmpty
    }

    fun processingInitData(period: String, fakeData: FakeModel) {
        idx = 0
        entryData.clear()
        lastEntryData.clear()
        xAxisValue.clear()

        fakeData.rentReport.socReportArray?.forEach { (date, value) ->
            entryData.add(Entry(idx.toFloat(), value.toFloat()))
            xAxisValue.add(if(period == "day") date else "${date}일")
            idx++
        }

        lastEntryData.add(entryData[entryData.lastIndex])
        _batteryUsageEntryData.value = entryData

//        _moveLast.value = true
        setMoveLast(true)
    }

    fun processingAddData(period: String, fakeData: FakeModel) {
        idx = 0
        var previousIdx = fakeData.rentReport.socReport.size.toFloat()
        entryData.forEach {
            it.x = previousIdx
            previousIdx++
        }

        fakeData.rentReport.socReportArray?.forEach { (date, value) ->
            entryData.add(idx, Entry(idx.toFloat(), value.toFloat()))
            xAxisValue.add(idx, if(period == "day") date else "${date}일")
            idx++
        }

        _batteryUsageEntryData.value = entryData
        setMoveLast(false)
    }


    

    override fun disposableClear() {
        onCleared()
    }
}