package com.dagger.daggerhiltnetworkconnection.presentation.ui.main

import androidx.lifecycle.*
import kr.dagger.domain.state.ApiResponse
import com.dagger.daggerhiltnetworkconnection.presentation.base.BaseViewModel
import com.orhanobut.logger.Logger
import kr.dagger.domain.entity.MainUserInfoEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.dagger.domain.usecase.MainUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainUseCase: MainUseCase) : BaseViewModel() {
    private val _userInfo: MutableLiveData<ApiResponse<MainUserInfoEntity>> = MutableLiveData()
    val userInfo: MutableLiveData<ApiResponse<MainUserInfoEntity>>
        get() = _userInfo

    fun searchUserInfoResult(owner: String?) {
        viewModelScope.launch {
            mainUseCase.execute(owner)
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