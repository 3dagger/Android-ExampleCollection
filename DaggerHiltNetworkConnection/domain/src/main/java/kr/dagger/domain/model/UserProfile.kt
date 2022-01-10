package kr.dagger.domain.model

data class UserProfile(
    val login: String?,
    val id: Int,
    val url: String?,
    val avatar_url: String?,
    val html_url: String?,
    val name: String?
)
