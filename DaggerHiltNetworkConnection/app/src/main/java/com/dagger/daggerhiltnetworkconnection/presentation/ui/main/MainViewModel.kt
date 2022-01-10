package com.dagger.daggerhiltnetworkconnection.presentation.ui.main

import androidx.lifecycle.*
import kr.dagger.domain.state.ApiResponse
import com.dagger.daggerhiltnetworkconnection.presentation.base.BaseViewModel
import com.orhanobut.logger.Logger
import kr.dagger.domain.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.dagger.domain.usecase.GetUserProfileUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getUserProfileUseCase: GetUserProfileUseCase) : BaseViewModel() {
    private val _userInfo: MutableLiveData<ApiResponse<UserProfile>> = MutableLiveData()
    val userInfo: MutableLiveData<ApiResponse<UserProfile>>
        get() = _userInfo

    fun searchUserInfoResult(owner: String?) {
        viewModelScope.launch {
            getUserProfileUseCase.execute(owner)
                .onStart {
                    Logger.d("onStart")
                    handleLoading(true)
                }
                .catch {
                    Logger.d("catch :: ${it.message}")
                    handleLoading(false)
                }
                .collect { values ->
                    handleLoading(false)
                    Logger.d("value :: $values")
                    _userInfo.value = values
                }

            }
        }
}