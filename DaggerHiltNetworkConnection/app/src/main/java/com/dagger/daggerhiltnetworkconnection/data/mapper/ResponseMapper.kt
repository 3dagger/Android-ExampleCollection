package com.dagger.daggerhiltnetworkconnection.data.mapper

import com.dagger.daggerhiltnetworkconnection.domain.main.entity.MainUserInfoEntity
import com.dagger.daggerhiltnetworkconnection.domain.model.UserInfoResponse
import com.dagger.daggerhiltnetworkconnection.data.network.status.ApiResponse

object ResponseMapper {

//    fun responseToUserInfo(response: ApiResponse<UserInfoResponse>): ApiResponse<List<MainUserInfoEntity>> {
//        return when(response) {
//            is ApiResponse.Success -> ApiResponse.Success(response.data.results, response.code)
//            is ApiResponse.ApiError -> ApiResponse.ApiError(response.message, response.code)
//            is ApiResponse.NetworkError -> ApiResponse.NetworkError(response.throwable)
//            is ApiResponse.NullResult -> ApiResponse.NullResult()
//            is ApiResponse.Loading -> ApiResponse.Loading()
//        }
//    }
}