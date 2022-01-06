package kr.dagger.data.remote

import com.dagger.daggerhiltnetworkconnection.Constants.PERSONAL_GIT_HUB_TOKEN
import kr.dagger.domain.main.entity.MainUserInfoEntity
import kr.dagger.data.ApiData
import kr.dagger.domain.entity.UserRepo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface RemoteService {
    @Headers("Authorization: $PERSONAL_GIT_HUB_TOKEN")
    @GET("${ApiData.API_GITHUB_USER_INFO}{userId}")
    suspend fun getUserInfo(@Path("userId") userId: String): Response<MainUserInfoEntity>

//    @Headers("Authorization: $PERSONAL_GIT_HUB_TOKEN")
//    @GET("${ApiData.API_GITHUB_USER_INFO}{userId}")
//    suspend fun getUserInfo(@Path("userId") userId: String): ApiResponse<UserInfoResponse>


//    @Headers("Authorization: $PERSONAL_GIT_HUB_TOKEN")
//    @GET("${ApiData.API_GITHUB_USER_INFO}{userId}")
//    suspend fun getUserInfo(@Path("userId") userId: String): MainUserInfoEntity

    @GET(ApiData.API_GITHUB_USER_REPO)
    suspend fun getUserRepos(@Path("owner") owner: String, @Header("Authorization") token: String): List<UserRepo>

    @GET(ApiData.API_GITHUB_USERS)
    suspend fun getUsers(): Response<MainUserInfoEntity>

}