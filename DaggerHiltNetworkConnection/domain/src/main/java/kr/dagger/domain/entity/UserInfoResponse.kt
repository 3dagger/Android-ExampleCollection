package kr.dagger.domain.entity

import kr.dagger.domain.main.entity.MainUserInfoEntity
//import com.squareup.moshi.JsonClass

//@JsonClass(generateAdapter = true)
data class UserInfoResponse(
    val results: List<MainUserInfoEntity>,
)
