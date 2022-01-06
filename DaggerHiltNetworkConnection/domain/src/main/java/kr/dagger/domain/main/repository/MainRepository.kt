package kr.dagger.domain.main.repository

import kr.dagger.domain.state.ApiResponse
import kr.dagger.domain.main.entity.MainUserInfoEntity
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getUserInfo(owner: String?): Flow<ApiResponse<MainUserInfoEntity>>
//    fun getUserInfo(owner: String?): Flow<ApiResponse<List<MainUserInfoEntity>>>
//    fun getUserInfo(owner: String?): Flow<MainUserInfoEntity>
}

