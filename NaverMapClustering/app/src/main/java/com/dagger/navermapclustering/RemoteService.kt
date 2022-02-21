package com.dagger.navermapclustering

import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteService {

//	@GET("https://api-dev.aims-rnd.com/api/rent/reportpage/{period}")
//	fun apiGetDailyRental(@Header("X-AUTH-TOKEN") token: String, @Path("period") period: String, @Query("page") page: Int) : Single<Response<FakeModel>>
}