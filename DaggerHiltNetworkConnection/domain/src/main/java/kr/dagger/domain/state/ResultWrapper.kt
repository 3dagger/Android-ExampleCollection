package kr.dagger.domain.state

sealed class ResultWrapper<out T> {

    data class Success<out T>(val value: T) : ResultWrapper<T>()

    data class GenericError(val code: Int? = null, val errorInfo: ErrorResponse? = null) : ResultWrapper<Nothing>()
}
