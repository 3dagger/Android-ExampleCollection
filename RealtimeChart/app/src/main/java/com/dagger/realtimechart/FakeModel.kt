package com.dagger.realtimechart

import com.google.gson.annotations.SerializedName

data class FakeModel(
    @SerializedName("page") val page: Page,
    @SerializedName("rentArrayReport") val rentReport: RentArrayReport
)

data class Page(
    @SerializedName("size") val size: Int,
    @SerializedName("totalElements") val totalElements: Int,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("number") val number: Int
)

data class RentArrayReport(
    @SerializedName("ratePlan") val ratePlan: RatePlan?,
    @SerializedName("socReportArray") val socReportArray: List<SocReportArray>?,
    @SerializedName("countReportArray") val countReportArray: List<CountReportArray>?,
    @SerializedName("mobilities") val mobilities: List<Mobilities>?,
    @SerializedName("time") val time: String?,
    @SerializedName("username") val userName: String?,
    @SerializedName("mileage") val mileage: Int
)

data class RatePlan(
    @SerializedName("userId") val userId: String?,
    @SerializedName("planName") val planName: String?,
    @SerializedName("nextPlanName") val nextPlanName: String?,
    @SerializedName("billKey") val billKey: String?,
    @SerializedName("orderId") val orderId: String?,
    @SerializedName("userName") val userName: String?,
    @SerializedName("delYn") val delYn: String?,
    @SerializedName("cancelYn") val cancelYn: String?
)

data class SocReportArray(
    @SerializedName("date") val date: String?,
    @SerializedName("value") val value: Int?,
    @SerializedName("usedKWH") val userKWH: Double?
)

data class CountReportArray(
    @SerializedName("date") val date: String?,
    @SerializedName("value") val value: Int?,
    @SerializedName("usedKWH") val usedKWH: Double?
)

data class Mobilities(
    @SerializedName("chassisNumber") val chassisNumber: String?,
    @SerializedName("userId") val userId: String?,
    @SerializedName("dealershipId") val dealershipId: String?,
    @SerializedName("dealership") val dealership: DealerShip?,
    @SerializedName("mobilityModel") val mobilityModel: MobilityModel?
)

data class DealerShip(
    @SerializedName("dealershipId") val dealershipId: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("address") val address: String?,
    @SerializedName("openingHours") val openingHours: String?,
    @SerializedName("phoneNumber") val phoneNumber: String?,
    @SerializedName("region") val region: String?,
    @SerializedName("lat") val lat: Double?,
    @SerializedName("lon") val lon: Double?,
    @SerializedName("imageUrl") val imageUrl: String?
)

data class MobilityModel(
    @SerializedName("chassisNumber") val chassisNumber: String?,
    @SerializedName("motorNumber") val motorNumber: String?,
    @SerializedName("model") val model: String?,
    @SerializedName("color") val color: String?,
    @SerializedName("imageUrl") val imageUrl: String?
)
//data class Page(
//    @SerializedName("size") val size: Int,
//    @SerializedName("totalElements") val totalElements: Int,
//    @SerializedName("totalPages") val totalPages: Int,
//    @SerializedName("number") val number: Int
//)
//
//data class RentReport(
//    @SerializedName("ratePlan") var ratePlan: RatePlan?,
//    @SerializedName("socReport") var socReport: Map<String, Int>,
//    @SerializedName("countReport") var countReport: Map<String, Int>,
//    @SerializedName("mobilities") var mobilities: List<Mobilities>?,
//    @SerializedName("socReportArray") var socReportArray: List<SocReportArray>?,
//    @SerializedName("countReportArray") var countReportArray: List<CountReportArray>?,
//    @SerializedName("time") var time: String?,
//    @SerializedName("username") var userName: String?,
//    @SerializedName("mileage") var mileage: Int
//)
//
//data class RatePlan(
//    @SerializedName("userId") var userId: String?,
//    @SerializedName("planName") var planName: String?,
//    @SerializedName("nextPlanName") var nextPlanName: String?,
//    @SerializedName("billKey") var billKey: String?,
//    @SerializedName("orderId") var orderId: String?,
//    @SerializedName("userName") var userName: String?,
//    @SerializedName("delYn") var delYn: String?,
//    @SerializedName("cancelYn") var cancelYn: String?
//)
//
//data class SocReportArray(
//    @SerializedName("date") val date: String,
//    @SerializedName("value") val value: Int
//)
//
//data class CountReportArray(
//    @SerializedName("date") val date: String,
//    @SerializedName("value") val value: Int
//)
//
//data class Mobilities(
//    @SerializedName("chassisNumber") var chassisNumber: String?,
//    @SerializedName("userId") var userId: String?,
//    @SerializedName("dealershipId") var dealershipId: String?,
//    @SerializedName("dealership") var dealership: DealerShip,
//    @SerializedName("mobilityModel") var mobilityModel: MobilityModel
//)
//
//data class DealerShip(
//    @SerializedName("dealershipId") var dealershipId: String?,
//    @SerializedName("name") var name: String?,
//    @SerializedName("address") var address: String?,
//    @SerializedName("openingHours") var openingHours: String?,
//    @SerializedName("phoneNumber") var phoneNumber: String?,
//    @SerializedName("region") var region: String?
//)
//
//data class MobilityModel(
//    @SerializedName("chassisNumber") var chassisNumber: String?,
//    @SerializedName("motorNumber") var motorNumber: String?,
//    @SerializedName("model") var model: String?,
//    @SerializedName("color") var color: String?,
//    @SerializedName("imageUrl") var imageUrl: String?
//)
//
//
//
