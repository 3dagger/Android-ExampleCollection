package kr.dagger.data.remote

import kr.dagger.data.network.base.BaseApiResponse
import kr.dagger.domain.main.entity.MainUserInfoEntity
import kr.dagger.domain.main.repository.MainRepository
import kr.dagger.domain.state.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepositoryImpl @Inject constructor(private val remoteService: RemoteService, ) : MainRepository, BaseApiResponse() {

    override fun getUserInfo(owner: String?): Flow<ApiResponse<MainUserInfoEntity>> = flow {
        val response = remoteService.getUserInfo(owner!!)
        emit( safeApiCall { response })
    }


//    override fun getUserInfo(owner: String?): Flow<ApiResponse<MainUserInfoEntity>> = flow {
//        val response = remoteService.getUserInfo(owner!!)
//        emit( safeApiCall { response })
//    }.flowOn(Dispatchers.IO)
}