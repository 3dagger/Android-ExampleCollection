package com.dagger.navermapclustering

import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.*

interface RemoteService {
//	@GET("https://7a7b97f0-1b1a-419e-9591-00f8fba48f4b.mock.pstmn.io/stations/search/filter")
	@POST("https://0505fc60-494d-4010-9936-6c5e2398a10c.mock.pstmn.io/api/station/search/filter")
	fun apiMockStationList() : Single<Response<StationAndDealership>>
}