package kr.dagger.domain.state

sealed class ApiResponse<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : ApiResponse<T>(data)

    class Loading<T>(data: T? = null) : ApiResponse<T>(data)

    class Error<T>(message: String, data: T? = null) : ApiResponse<T>(data, message)
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
