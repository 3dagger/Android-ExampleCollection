package com.dagger.daggerhiltnetworkconnection.presentation.ui.user.info

import android.os.Bundle
import androidx.activity.viewModels
import com.dagger.daggerhiltnetworkconnection.Constants.INTENT_ARGUMENT_USER_ID
import com.dagger.daggerhiltnetworkconnection.R
import com.dagger.daggerhiltnetworkconnection.presentation.base.BaseActivity
import com.dagger.daggerhiltnetworkconnection.databinding.ActivityDetailBinding
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserInfoActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {
    private val viewModel: UserInfoViewModel by viewModels()

    private var argumentUserId: String? = null

    override fun initView(savedInstanceState: Bundle?) {
        binding {
            activity = this@UserInfoActivity
            vm = viewModel
        }

        if(intent != null) argumentUserId = intent.getStringExtra(INTENT_ARGUMENT_USER_ID)


    }

    override fun onProcess() {
//        viewModel.getUserRepositories(owner = argumentUserId)
//        viewModel.userRepositories.observe(this@DetailActivity, Observer {
//            Logger.d("res :: $it")
//        })

//        viewModel.getUserInfo(userId = argumentUserId)

//        viewModel.userInfo.observe(this@DetailActivity, Observer {
//            binding.webView.loadUrl(it.data?.htmlUrl.toString())
//        })
    }
}