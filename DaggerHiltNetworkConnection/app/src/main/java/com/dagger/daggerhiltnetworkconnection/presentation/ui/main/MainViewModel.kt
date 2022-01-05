package com.dagger.daggerhiltnetworkconnection.presentation.ui.main

import androidx.lifecycle.*
import com.dagger.daggerhiltnetworkconnection.data.network.status.ApiResponse
import com.dagger.daggerhiltnetworkconnection.presentation.base.BaseViewModel
import com.dagger.daggerhiltnetworkconnection.domain.main.entity.MainUserInfoEntity
import com.dagger.daggerhiltnetworkconnection.domain.main.usecase.MainUseCase
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainUseCase: MainUseCase) : BaseViewModel() {
//    private val _userInfo: MutableLiveData<Resource<UserInfo>> = MutableLiveData()
//    val userInfo: MutableLiveData<Resource<UserInfo>>
//        get() = _userInfo



    private val _userInfo: MutableLiveData<ApiResponse<MainUserInfoEntity>> = MutableLiveData()
    val userInfo: MutableLiveData<ApiResponse<MainUserInfoEntity>>
        get() = _userInfo

    fun searchUserInfoResult(owner: String?) {
        viewModelScope.launch {
            mainUseCase.execute(owner)
                .catch {
                    Logger.d("exception ")
                }
                .collect { values ->
                    Logger.d("values :: $values.")
                    _userInfo.value = values
                }

            }
        }
//        viewModelScope.launch {
//            mainUseCase.execute(owner)
//                .catch { e ->
//                    Logger.d("exception message :: ${e.message}\nlocalizedMessage :: ${e.localizedMessage}\ncause :: ${e.cause}")
//                }
////                .filterIsInstance<ApiResponse.Success<List<MainUserInfoEntity>>>()
//                .collect {
//                    Logger.d("it :: $it")
//                    _userInfo.value = it
//                }
//
//        }




//    fun searchUserInfoResult(owner: String?) {
//        mainUseCase.execute(owner)
//            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
//            .filterIsInstance<ApiResponse.Success<UserInfo>>()
//            .map { it.data }
//            .asLiveData()
//    }

//    fun getUserInfo(userId: String?) {
//        viewModelScope.launch {
//            mainUseCase.getUserInfo(userId = userId?: "3dagger", onStart = { handleLoading(true) }, onComplete = { handleLoading(false) }, onError = { handleLoading(false)})
//                .onEach {
//                    _userInfo.value = it
//                }
//                .launchIn(viewModelScope)
//        }
//    }


//    private val _userRepositories = MutableLiveData<List<UserRepo>>()
//    val userRepositories: LiveData<List<UserRepo>>
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