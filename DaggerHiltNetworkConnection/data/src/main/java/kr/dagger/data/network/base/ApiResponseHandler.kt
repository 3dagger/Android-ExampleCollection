package kr.dagger.data.network.base


import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kr.dagger.domain.state.ApiResponse
import kr.dagger.domain.state.ErrorResponse
import kr.dagger.domain.state.ResultWrapper
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class ApiResponseHandler {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ApiResponse<T> {
        try {
            ApiResponse.Loading(null)
            val response = apiCall()
            if(response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return ApiResponse.Success(body)
                }
            }else {
                Logger.d("response is not successful :: ${response.errorBody()?.string()}")
//                ApiResponse.Error(errorBodyHandle(response.errorBody(), ))
            }
//            val errorMessage = response.errorBody()!!.string()
            return ApiResponse.Error("")
        }catch (e: Exception) {
            return ApiResponse.Error("Fail")
//            ApiResponse.Error(message = e.message, )

        }
    }

//    protected abstract fun errorBodyHandle(message: String, documentation_url: String)


//    val NETWORK_ERROR_UNKNWON = 110
//    val NETWORK_ERROR_TIMEOUT = 111
//    val NETWORK_ERROR_CONNECT = 112
//    val NETWORK_ERROR_IOEXCEPTION = 113
//
//    suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): ResultWrapper<T> {
//        return withContext(dispatcher) {
//            try {
//                ResultWrapper.Success(apiCall.invoke())
//            } catch (throwable: Throwable) {
//                when (throwable) {
//                    is IOException -> {
//                        val code = NETWORK_ERROR_TIMEOUT
//                        ResultWrapper.GenericError(code, null)
//                    }
//                    is SocketTimeoutException -> {
//                        val code = NETWORK_ERROR_TIMEOUT
//                        ResultWrapper.GenericError(code, ErrorResponse("", "",""))
//                    }
//                    is UnknownHostException -> {
//                        val code = NETWORK_ERROR_TIMEOUT
//                        ResultWrapper.GenericError(code, ErrorResponse("", "",""))
//                    }
//                    is HttpException -> {
//                        val code = throwable.code()
//                        ResultWrapper.GenericError(code, ErrorResponse("", "", ""))
//                    }
//                    else -> {
//                        ResultWrapper.GenericError(null, null)
//                    }
//                }
//            }
//        }
//    }

//    private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
//        return try {
//            throwable.response()?.errorBody()?.source()?.let {
//                val moshiAdapter = Moshi.Builder().build()
//                    .adapter(ErrorResponse::class.java) moshiAdapter . fromJson (it)
//            }
//        } catch (exception: Exception) {
//            null
//        }
//    }


}