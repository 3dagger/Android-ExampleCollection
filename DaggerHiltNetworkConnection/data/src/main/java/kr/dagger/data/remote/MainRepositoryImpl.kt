package kr.dagger.data.remote

import kr.dagger.data.network.base.ApiResponseHandler
import kr.dagger.domain.model.UserProfile
import kr.dagger.domain.repository.MainRepository
import kr.dagger.domain.state.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kr.dagger.domain.model.UserRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepositoryImpl @Inject constructor(private val remoteService: RemoteService) : MainRepository, ApiResponseHandler() {

    override fun getUserInfo(owner: String?): Flow<ApiResponse<UserProfile>> = flow {
        val response = remoteService.getUserInfo(owner!!)
        emit( safeApiCall { response })
    }.flowOn(Dispatchers.IO)

    override fun getUserRepositories(owner: String): Flow<ApiResponse<List<UserRepo>>> = flow {
        emit( safeApiCall { remoteService.getUserRepos(owner) })
    }.flowOn(Dispatchers.IO)


//    override fun getUserInfo(owner: String?): Flow<ApiResponse<MainUserInfoEntity>> = flow {
//        val response = remoteService.getUserInfo(owner!!)
//        emit( safeApiCall { response })
//    }.flowOn(Dispatchers.IO)
}