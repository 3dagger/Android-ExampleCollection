package kr.dagger.domain.state

//data class ApiResponse<out T>(val status: Status, val data: T?, val message: String?) {
//
//    enum class Status { SUCCESS, ERROR, LOADING, FAILURE }
//
//    companion object {
//
//        fun <T> success(data: T): ApiResponse<T> {
//            return ApiResponse(Status.SUCCESS, data, null)
//        }
//
//        fun <T> error(message: String, data: T? = null): ApiResponse<T> {
//            return ApiResponse(Status.ERROR, data, message)
//        }
//
//        fun<T> loading(data: T?): ApiResponse<T> {
//            return ApiResponse(Status.LOADING, data, null)
//        }
//
//        fun <T> failed(message: String, data: T? = null): ApiResponse<T> {
//            return ApiResponse(Status.FAILURE, data, message)
//        }
//    }
//}

sealed class ApiResponse<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : ApiResponse<T>(data)

    class Error<T>(message: String, data: T? = null) : ApiResponse<T>(data, message)

    class Loading<T> : ApiResponse<T>()
}

//sealed class ApiResponse<T> {
//
//    class Success<T>(val data: T, val code: Int) : ApiResponse<T>()
//
//    class Loading<T> : ApiResponse<T>()
//
//    class ApiError<T>(val message: String, val code: Int) : ApiResponse<T>()
//
//    class NetworkError<T>(val throwable: Throwable) : ApiResponse<T>()
//
//    class NullResult<T> : ApiResponse<T>()
//}
