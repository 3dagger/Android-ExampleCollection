package com.dagger.daggerhiltnetworkconnection.presentation.ui.main


import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.dagger.daggerhiltnetworkconnection.BuildConfig
import com.dagger.daggerhiltnetworkconnection.R
import com.dagger.daggerhiltnetworkconnection.presentation.base.BaseActivity
import com.dagger.daggerhiltnetworkconnection.databinding.ActivityMainBinding
import com.dagger.daggerhiltnetworkconnection.presentation.extensions.openActivity
import com.dagger.daggerhiltnetworkconnection.presentation.extensions.textChange
import com.dagger.daggerhiltnetworkconnection.presentation.ui.user.info.UserInfoActivity
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

        viewModel.userInfo.observe(this) {
            Logger.d("userInfo it :: ${it.message}")
        }


    }

    private fun initViewSetup() {
        lifecycleScope.launch {
            binding.edtSearchText.apply {
                textChange()
                    .debounce(500)
                    .filter {  it?.length!! > 0 }
                    .onEach {
                        viewModel.searchUserInfoResult(owner = it.toString())
//                        viewModel.searchUserProfile(owner = it.toString())
                    }
                    .launchIn(this@launch)
            }
        }
    }

    fun moveUserInfoActivity() {
        openActivity(UserInfoActivity::class.java) {
//            putString()
        }
    }
}
