package com.dagger.realtimechart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dagger.realtimechart.base.BaseActivity
import com.dagger.realtimechart.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator.View{
    override val viewModel: MainViewModel by inject()
    override val layoutResourceId: Int get() = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        viewModel.setNavigator(this@MainActivity)
    }

    override fun onProcess() {
    }

    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }
}