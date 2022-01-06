package com.dagger.daggerhiltnetworkconnection.presentation.ui.main

import androidx.lifecycle.*
import kr.dagger.domain.state.ApiResponse
import com.dagger.daggerhiltnetworkconnection.presentation.base.BaseViewModel
import kr.dagger.domain.main.entity.MainUserInfoEntity
import kr.dagger.domain.main.usecase.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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
                    handleLoading(true)
                }
                .catch {
                    handleLoading(false)
                }
                .collect { values ->
                    handleLoading(false)
                    _userInfo.value = values
                }

            }
        }
}