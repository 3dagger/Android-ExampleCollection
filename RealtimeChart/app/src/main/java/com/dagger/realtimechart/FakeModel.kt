package com.dagger.realtimechart

import com.google.gson.annotations.SerializedName

data class FakeModel(
    @SerializedName("page") val page: Page,
    @SerializedName("rentReport") val rentReport: RentReport
)

data class Page(
    @SerializedName("size") val size: Int,
    @SerializedName("totalElements") val totalElements: Int,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("number") var number: Int
)

data class RentReport(
    @SerializedName("ratePlan") var ratePlan: RatePlan?,
    @SerializedName("socReport") var socReport: Map<String, Int>,
    @SerializedName("countReport") var countReport: Map<String, Int>,
    @SerializedName("mobilities") var mobilities: List<Mobilities>?,
    @SerializedName("socReportArray") var socReportArray: List<SocReportArray>?,
    @SerializedName("countReportArray") var countReportArray: List<CountReportArray>?,
    @SerializedName("time") var time: String?,
    @SerializedName("username") var userName: String?,
    @SerializedName("mileage") var mileage: Int
)

data class RatePlan(
    @SerializedName("userId") var userId: String?,
    @SerializedName("planName") var planName: String?,
    @SerializedName("nextPlanName") var nextPlanName: String?,
    @SerializedName("billKey") var billKey: String?,
    @SerializedName("orderId") var orderId: String?,
    @SerializedName("userName") var userName: String?,
    @SerializedName("delYn") var delYn: String?,
    @SerializedName("cancelYn") var cancelYn: String?
)

data class SocReportArray(
    @SerializedName("date") val date: String,
    @SerializedName("value") val value: Int
)

data class CountReportArray(
    @SerializedName("date") val date: String,
    @SerializedName("value") val value: Int
)

data class Mobilities(
    @SerializedName("chassisNumber") var chassisNumber: String?,
    @SerializedName("userId") var userId: String?,
    @SerializedName("dealershipId") var dealershipId: String?,
    @SerializedName("dealership") var dealership: DealerShip,
    @SerializedName("mobilityModel") var mobilityModel: MobilityModel
)

data class DealerShip(
    @SerializedName("dealershipId") var dealershipId: String?,
    @SerializedName("name") var name: String?,
    @SerializedName("address") var address: String?,
    @SerializedName("openingHours") var openingHours: String?,
    @SerializedName("phoneNumber") var phoneNumber: String?,
    @SerializedName("region") var region: String?
)

data class MobilityModel(
    @SerializedName("chassisNumber") var chassisNumber: String?,
    @SerializedName("motorNumber") var motorNumber: String?,
    @SerializedName("model") var model: String?,
    @SerializedName("color") var color: String?,
    @SerializedName("imageUrl") var imageUrl: String?
)



