package com.dagger.daggerhiltnetworkconnection.presentation.ui.user.info

import androidx.lifecycle.MutableLiveData
import com.dagger.daggerhiltnetworkconnection.presentation.base.BaseViewModel
import kr.dagger.domain.entity.MainUserInfoEntity
import com.dagger.daggerhiltnetworkconnection.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.dagger.domain.usecase.MainUseCase
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(private val mainUseCase: MainUseCase) : BaseViewModel() {
    private val _userInfo: MutableLiveData<Resource<MainUserInfoEntity>> = MutableLiveData()
    val userInfo: MutableLiveData<Resource<MainUserInfoEntity>>
        get() = _userInfo



    fun getUserInfo(userId: String?) {
//        viewModelScope.launch {
//            mainUseCase.getUserInfo(userId = userId?: "3dagger", onStart = {  }, onComplete = {  }, onError = { Logger.d("onError")})
//                .onEach {
//                    Logger.d("userInfo :: $it")
//                    _userInfo.value = it
//                }
//                .launchIn(viewModelScope)
//        }
    }



//    private val _userRepositories = MutableLiveData<Resource<List<UserRepo>>>()
//    val userRepositories: LiveData<Resource<List<UserRepo>>>
//        get() = _userRepositories
//
//    fun getUserRepositories(owner: String?) {
//        viewModelScope.launch {
//            mainUseCase.getUserRepo(owner = owner)
//                .onEach {
//                    _userRepositories.value = it
//                }
//                .launchIn(viewModelScope)
//        }
//    }

}