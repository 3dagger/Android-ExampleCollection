//package com.dagger.daggerhiltnetworkconnection.data.network.retrofit.factory
//
//import retrofit2.Call
//import com.dagger.daggerhiltnetworkconnection.data.network.status.ApiResponse
//import com.orhanobut.logger.Logger
//import okhttp3.Request
//import okio.Timeout
//import retrofit2.Callback
//import retrofit2.Response
//
//
//internal class ResponseCall<T> constructor(private val callDelegate: Call<T>) : Call<ApiResponse<T>> {
//
//    override fun enqueue(callback: Callback<ApiResponse<T>>) = callDelegate.enqueue(object : Callback<T> {
//        override fun onResponse(call: Call<T>, response: Response<T>) {
//            response.body()?.let {
//                when(response.code()) {
//                    in 200..208 -> {
//                        callback.onResponse(this@ResponseCall, Response.success(ApiResponse.Success(it, response.code())))
//                    }
//                    in 400..409 -> {
//                        callback.onResponse(this@ResponseCall, Response.success(ApiResponse.ApiError(response.message(), response.code())))
//                    }
//                }
//            }?: callback.onResponse(this@ResponseCall, Response.success(ApiResponse.NullResult()))
//        }
//
//        override fun onFailure(call: Call<T>, t: Throwable) {
//            callback.onResponse(this@ResponseCall, Response.success(ApiResponse.NetworkError(t)))
//            call.cancel()
//        }
//    })
//
//    override fun clone(): Call<ApiResponse<T>> = ResponseCall(callDelegate.clone())
//
//    override fun execute(): Response<ApiResponse<T>> = throw UnsupportedOperationException("ResponseCall does not support execute.")
//
//    override fun isExecuted(): Boolean = callDelegate.isExecuted
//
//    override fun cancel() = callDelegate.cancel()
//
//    override fun isCanceled(): Boolean = callDelegate.isCanceled
//
//    override fun request(): Request = callDelegate.request()
//
//    override fun timeout(): Timeout = callDelegate.timeout()
//}