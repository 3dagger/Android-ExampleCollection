package com.dagger.custommarkerview

import com.dagger.custommarkerview.di.Station
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RemoteService {
    @GET("https://7a7b97f0-1b1a-419e-9591-00f8fba48f4b.mock.pstmn.io/stations/search/filter")
    fun apiMockStationList() : Single<Response<Station>>

//    @POST("https://api-nanu.aims-rnd.com:9010/api/stations/search/filter")
//    fun apiMockStationList(@Header("X-AUTH-TOKEN") token: String, @Header("X-API-VERSION") apiVersion: String, @Body filter: EntiretyStationReq) : Single<Response<Station>>
}

//token = Prefs.getString(Constants.PREFS_JWT_TOKEN,""),
//apiVersion = Constants.API_VERSION_TWO,
//filter = EntiretyStationReq(lat = null, lon = null, distance = null, soc = null)

data class EntiretyStationReq(
    @SerializedName("lat") var lat: Double?,
    @SerializedName("lon") var lon: Double?,
    @SerializedName("distance") var distance: Int?,
    @SerializedName("soc") var soc: Double?
)