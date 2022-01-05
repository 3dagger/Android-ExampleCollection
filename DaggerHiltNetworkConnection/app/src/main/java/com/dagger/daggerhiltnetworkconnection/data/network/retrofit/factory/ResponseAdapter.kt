//package com.dagger.daggerhiltnetworkconnection.data.network.retrofit.factory
//
//import com.dagger.daggerhiltnetworkconnection.data.network.status.ApiResponse
//import retrofit2.Call
//import retrofit2.CallAdapter
//import java.lang.reflect.Type
//
//internal class ResponseAdapter<T>(private val successType: Type) : CallAdapter<T, Call<ApiResponse<T>>> {
//    override fun responseType(): Type = successType
//
//    override fun adapt(call: Call<T>): Call<ApiResponse<T>> = ResponseCall(call)
//
//}