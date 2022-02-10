package com.dagger.realtimechart.base

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import java.lang.ref.WeakReference

open class BaseViewModel<N> : ViewModel() {
    private lateinit var mNavigator : WeakReference<N>
    private lateinit var handler    : Handler

    private val _isProgress = MutableLiveData<Boolean>()
    val isProgress : MutableLiveData<Boolean>
        get() = _isProgress

    private val compositeDisposable = CompositeDisposable()

    init {
        _isProgress.value = false
    }

    fun getNavigator(): N {
        return mNavigator.get()!!
    }

    fun setNavigator(navigator: N) {
        this.mNavigator = WeakReference(navigator)
    }

    fun getHandler(): Handler {
        return handler
    }

    fun setHandler(handler: Handler) {
        this.handler = handler
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    protected fun showProgress() {
        _isProgress.value = true
    }

    protected fun dismissProgress() {
        _isProgress.value = false
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}