package com.dagger.daggerhiltnetworkconnection.presentation.ui.main.detail.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dagger.daggerhiltnetworkconnection.presentation.base.BaseViewModel
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kr.dagger.domain.model.UserRepo
import kr.dagger.domain.state.ApiResponse
import kr.dagger.domain.usecase.GetPersonalGithubRepository
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val getPersonalGithubRepository: GetPersonalGithubRepository) : BaseViewModel() {
    private val _repoData: MutableLiveData<ApiResponse<List<UserRepo>>> = MutableLiveData()
    val repoData: LiveData<ApiResponse<List<UserRepo>>>
        get() = _repoData


    fun searchUserInfoResult(owner: String) {
        viewModelScope.launch {
            getPersonalGithubRepository.execute(owner)
                .onStart { handleLoading(true) }
                .catch { handleLoading(false) }
                .collect { values ->
                    _repoData.value = values
                }
        }
    }
}