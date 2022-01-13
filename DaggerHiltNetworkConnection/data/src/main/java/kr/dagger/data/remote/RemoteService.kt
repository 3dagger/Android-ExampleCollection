package kr.dagger.data.remote

import kr.dagger.domain.model.UserProfile
import kr.dagger.data.ApiData.API_GITHUB_USER_INFO
import kr.dagger.data.ApiData.API_GITHUB_USER_REPO
import kr.dagger.data.ApiData.PERSONAL_GITHUB_TOKEN
import kr.dagger.domain.model.UserRepo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface RemoteService {
    @Headers("Authorization: $PERSONAL_GITHUB_TOKEN")
    @GET("${API_GITHUB_USER_INFO}{userId}")
    suspend fun getUserInfo(@Path("userId") userId: String): Response<UserProfile>

    @Headers("Authorization: $PERSONAL_GITHUB_TOKEN")
    @GET(API_GITHUB_USER_REPO)
    suspend fun getUserRepos(@Path("owner") owner: String): Response<List<UserRepo>>

}