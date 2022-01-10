package kr.dagger.domain.state

data class ErrorResponse(
    val code: String,
    val message: String,
    val status: String
)
