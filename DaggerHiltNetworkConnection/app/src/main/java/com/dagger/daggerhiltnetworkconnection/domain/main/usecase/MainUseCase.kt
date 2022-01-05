package com.dagger.daggerhiltnetworkconnection.domain.main.usecase

import com.dagger.daggerhiltnetworkconnection.data.network.status.ApiResponse
import com.dagger.daggerhiltnetworkconnection.domain.main.entity.MainUserInfoEntity
import com.dagger.daggerhiltnetworkconnection.domain.main.repository.MainRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainUseCase @Inject constructor(private val mainRepository: MainRepository, private val ioDispatcher: CoroutineDispatcher) {
    /**
     * @author : 이수현
     * @Date : 2021/12/27 7:03 오후
     * @Description : 깃헙 유저정보 호출
     * @History :
     *
     **/
    fun execute(owner: String?) : Flow<ApiResponse<MainUserInfoEntity>> {
        return mainRepository.getUserInfo(owner)
    }








//    suspend fun getUserInfo(userId: String, onStart: () -> Unit, onComplete: () -> Unit, onError: () -> Unit): Flow<Resource<UserInfo>> = flow{
//        emit(Resource.loading())
//        try{
//            val response = mainRepository.getUserInfo(userId)
//            Logger.d("response :: $response")
//            emit(Resource.success(response.body()!!))
//        }catch (e: Exception) {
//            Logger.d("error :: ${e.message}")
//        }
//    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)


//    suspend fun getUserInfo(userId: String, onStart: () -> Unit, onComplete: () -> Unit, onError: () -> Unit): Flow<Resource<UserInfo>> = flow{
//        emit(Resource.loading())
//        try{
//            val response = mainRepository.getUserInfo(userId)
//            Logger.d("response :: $response")
//            emit(Resource.success(response.body()!!))
//        }catch (e: Exception) {
//            Logger.d("error :: ${e.message}")
//        }
//    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)

}