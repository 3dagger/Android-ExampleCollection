package kr.dagger.domain.model

data class UserRepo(
    val name: String,
    val id: Long,
    val date: String,
    val owner: Owner,
    val language: String,
    val created_at: String,
    val html_url: String
) {
    data class Owner(
        val avatar_url: String
    )
}