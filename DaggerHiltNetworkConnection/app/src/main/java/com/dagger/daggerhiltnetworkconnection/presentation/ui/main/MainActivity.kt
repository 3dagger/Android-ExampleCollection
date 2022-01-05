package com.dagger.daggerhiltnetworkconnection.presentation.ui.main

import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import androidx.activity.viewModels
import com.dagger.daggerhiltnetworkconnection.R
import com.dagger.daggerhiltnetworkconnection.presentation.base.BaseActivity
import com.dagger.daggerhiltnetworkconnection.databinding.ActivityMainBinding
import com.dagger.daggerhiltnetworkconnection.presentation.extensions.textChange
import com.dagger.daggerhiltnetworkconnection.utils.Resource.Status.*
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val viewModel: MainViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        binding {
            activity = this@MainActivity
            vm = viewModel
        }


    }

    override fun onProcess() {
        initViewSetup()
        subscribeObservers()
    }

    private fun initViewSetup() {
//        customProgress = CustomProgress(context = this@MainActivity)

        GlobalScope.launch {
            binding.edtSearchText.apply {
                textChange()
                    .debounce(500)
                    .filter {  it?.length!! > 0 }
                    .onEach {
                        Logger.d("flow로 받는다 $it")
//                        customProgress.show()
//                        viewModel.getUserInfo(userId = it.toString())
                        viewModel.searchUserInfoResult(owner = it.toString())
                    }
                    .launchIn(this@launch)
            }
        }

        viewModel.userInfo.observe(this) {
            Logger.d("res :: $it")
        }



//        binding.edtSearchText.apply {
//            this.filters = arrayOf(InputFilter.LengthFilter(30))
////            this.setTextColor(Color.WHITE)
////            this.setHintTextColor(Color.WHITE)
//        }
    }

    private fun subscribeObservers() {
        viewModel.isLoading.observe(this) {
            Logger.d("isLoading :: $it")
        }
    }


}
