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
    val isSearchEnabled = MutableLiveData<Boolean>()

    private val _userInfo: MutableLiveData<ApiResponse<UserProfile>> = MutableLiveData()
    val userInfo: LiveData<ApiResponse<UserProfile>>
        get() = _userInfo

    init {
        isSearchEnabled.value = false
    }

    fun searchUserInfoResult(owner: String?) {
        viewModelScope.launch {
            getUserProfileUseCase.execute(owner)
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