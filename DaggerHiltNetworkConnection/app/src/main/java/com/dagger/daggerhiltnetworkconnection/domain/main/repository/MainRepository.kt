package com.dagger.daggerhiltnetworkconnection.domain.main.repository

import com.dagger.daggerhiltnetworkconnection.data.network.status.ApiResponse
import com.dagger.daggerhiltnetworkconnection.domain.main.entity.MainUserInfoEntity
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getUserInfo(owner: String?): Flow<ApiResponse<MainUserInfoEntity>>
//    fun getUserInfo(owner: String?): Flow<ApiResponse<List<MainUserInfoEntity>>>
//    fun getUserInfo(owner: String?): Flow<MainUserInfoEntity>
}

