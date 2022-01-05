package com.dagger.daggerhiltnetworkconnection.domain.model

import com.dagger.daggerhiltnetworkconnection.domain.main.entity.MainUserInfoEntity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfoResponse(
    val results: List<MainUserInfoEntity>,
)
