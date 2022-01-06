package kr.dagger.data.remote

import kr.dagger.domain.main.entity.MainUserInfoEntity
import kr.dagger.data.ApiData
import kr.dagger.domain.entity.UserRepo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface RemoteService {
//    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
//    @Headers("Authorization: $PERSONAL_GIT_HUB_TOKEN")
    @Headers("Authorization: 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")
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