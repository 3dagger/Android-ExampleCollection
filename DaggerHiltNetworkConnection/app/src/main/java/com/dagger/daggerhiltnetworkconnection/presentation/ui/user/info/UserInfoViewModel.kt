package com.dagger.daggerhiltnetworkconnection.presentation.ui.user.info

import androidx.lifecycle.MutableLiveData
import com.dagger.daggerhiltnetworkconnection.presentation.base.BaseViewModel
import kr.dagger.domain.model.UserProfile
import com.dagger.daggerhiltnetworkconnection.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.dagger.domain.usecase.GetUserProfileUseCase
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(private val getUserProfileUseCase: GetUserProfileUseCase) : BaseViewModel() {
    private val _userInfo: MutableLiveData<Resource<UserProfile>> = MutableLiveData()
    val userInfo: MutableLiveData<Resource<UserProfile>>
        get() = _userInfo



    fun getUserInfo(userId: String?) {
//        viewModelScope.launch {
//            getUserProfileUseCase.getUserInfo(userId = userId?: "3dagger", onStart = {  }, onComplete = {  }, onError = { Logger.d("onError")})
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
//            getUserProfileUseCase.getUserRepo(owner = owner)
//                .onEach {
//                    _userRepositories.value = it
//                }
//                .launchIn(viewModelScope)
//        }
//    }

}