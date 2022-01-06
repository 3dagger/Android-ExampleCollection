package com.dagger.daggerhiltnetworkconnection.presentation.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import javax.inject.Inject


abstract class BaseActivity<T : ViewDataBinding> constructor(@LayoutRes val contentLayoutId: Int): AppCompatActivity() {
    private var bindingComponent: DataBindingComponent? = DataBindingUtil.getDefaultComponent()
//    @Inject lateinit var networkConnection: NetworkConnection

    init {
        addOnContextAvailableListener { binding.notifyChange() }
    }

    val binding: T by lazy(LazyThreadSafetyMode.NONE) {
        DataBindingUtil.setContentView(this, contentLayoutId, bindingComponent)
    }

    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun onProcess()

    inline fun binding(block: T.() -> Unit): T {
        return binding.apply(block)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this@BaseActivity

//        networkConnection.observe(this@BaseActivity, Observer { isConnected ->
//            when(isConnected) {
//                true -> toast("네트워크 연결되었습니다.")
//                false -> toast("네트워크 연결 해제되었습니다.")
//            }
//        })

        initView(savedInstanceState = savedInstanceState)
        onProcess()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

}