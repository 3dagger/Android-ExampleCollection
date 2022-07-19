package com.dagger.realtimechart

import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteService {

//    @GET("https://0505fc60-494d-4010-9936-6c5e2398a10c.mock.pstmn.io/report/day")
//    fun apiGetDailyRental(@Header("X-AUTH-TOKEN") token: String, @Query("page") page: Int) : Single<Response<FakeModel>>

    @GET("https://api-dev.aims-rnd.com/api/rent/reportpage/{period}")
    fun apiGetDailyRental(@Header("X-AUTH-TOKEN") token: String, @Path("period") period: String, @Query("page") page: Int) : Single<Response<FakeModel>>

//    @GET("https://0505fc60-494d-4010-9936-6c5e2398a10c.mock.pstmn.io/report/day")
//    fun apiGetDailyRental(@Query("page") page: String) : Single<Response<FakeModel>>
}