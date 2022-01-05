package com.dagger.daggerhiltnetworkconnection.data.remote

import com.dagger.daggerhiltnetworkconnection.data.network.base.BaseApiResponse
import com.dagger.daggerhiltnetworkconnection.domain.main.entity.MainUserInfoEntity
import com.dagger.daggerhiltnetworkconnection.domain.model.UserInfoResponse
import com.dagger.daggerhiltnetworkconnection.domain.main.repository.MainRepository
import com.dagger.daggerhiltnetworkconnection.data.network.status.ApiResponse
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepositoryImpl @Inject constructor(
    private val remoteService: RemoteService,
//    private val remoteService: RemoteService,
//    private val responseToUserInfo: (ApiResponse<UserInfoResponse>) -> ApiResponse<List<MainUserInfoEntity>>
) : MainRepository, BaseApiResponse() {
//    override fun getUserInfo(owner: String?): Flow<ApiResponse<UserInfo>> = flow {
//        val response = remoteService.getUserInfo(owner?: "3dagger")
//        emit(response)
//    }

//    override fun getUserInfo(owner: String?): Flow<ApiResponse<UserInfo>> = flow {
//        val response = remoteService.getUserInfo1(owner?: "3dagger")
//        emit(responseToUserInfo(response))
//    }

//    override fun getUserInfo(owner: String?): Flow<MainUserInfoEntity> = flow {
//        emit(remoteService.getUserInfo(owner?: "3dagger"))
//    }

//    override fun getUserInfo(owner: String?): Flow<ApiResponse<List<MainUserInfoEntity>>> = flow {
//        val response = remoteService.getUserInfo(owner!!)
//        Logger.d("response :: $response")
//        emit(responseToUserInfo(response))
////        remoteService.getUserInfo(owner!!).run {
////            Logger.d("this :: ${this}")
////            emit(responseToUserInfo(this))
////        }
////        val response = remoteService.getUserInfo(owner!!)
////        emit(response)
////        emit(responseToUserInfo(response))
//
////        emit(response)
////        emit(remoteService.getUserInfo(owner?: "3dagger"))
//    }

    override fun getUserInfo(owner: String?): Flow<ApiResponse<MainUserInfoEntity>> = flow {
        val response = remoteService.getUserInfo(owner!!)
        emit( safeApiCall { response })
    }.flowOn(Dispatchers.IO)
}