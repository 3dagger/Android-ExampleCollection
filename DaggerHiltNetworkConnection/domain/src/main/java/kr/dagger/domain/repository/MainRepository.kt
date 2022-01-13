package kr.dagger.domain.repository

import kr.dagger.domain.state.ApiResponse
import kr.dagger.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kr.dagger.domain.model.UserRepo

interface MainRepository {

    fun getUserInfo(owner: String?): Flow<ApiResponse<UserProfile>>

    fun getUserRepositories(owner: String): Flow<ApiResponse<List<UserRepo>>>

}

