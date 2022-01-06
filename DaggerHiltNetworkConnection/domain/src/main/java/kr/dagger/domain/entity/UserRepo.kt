package kr.dagger.domain.entity

//import com.squareup.moshi.Json
//import com.squareup.moshi.JsonClass
//
//@JsonClass(generateAdapter = true)
//data class UserRepo(
//    @field:Json(name = "name") val name: String,
//    @field:Json(name = "id") val id: Long,
////    @field:Json(name = "date") val date: String,
//    @field:Json(name = "url") var url: String
//)

data class UserRepo(
    val name: String,
    val id: Long,
    val date: String,
    val url: String
)