package com.dagger.navermapclustering

import com.google.gson.annotations.SerializedName

data class StationAndDealership(
    @SerializedName("stationList") val stationList: List<StationList>,
    @SerializedName("dealershipList") val dealershipList: List<DealershipList>
)

data class StationList(
    @SerializedName("stationId") val stationId: String?,
    @SerializedName("parentId") val parentId: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("lat") val lat: Double?,
    @SerializedName("lon") val lon: Double?,
    @SerializedName("address1") val address1: String?,
    @SerializedName("address2") val address2: String?,
    @SerializedName("slots") val slots: List<Slots>?,
    @SerializedName("socCount") val socCount: Int?,
    @SerializedName("maxSoc") val maxSoc: Double?,
    @SerializedName("imageUri") val imageUri: String?,
    @SerializedName("bookmark") val bookmark: Boolean?,
    @SerializedName("isServiceable") val isServiceable: Boolean
)

data class Slots(
    @SerializedName("stationId") val stationId: String?,
    @SerializedName("tower") val tower: String?,
    @SerializedName("slot") val slot: String?,
    @SerializedName("batteryId") val batteryId: String?,
    @SerializedName("errorCode") val errorCode: String?,
    @SerializedName("battery") val battery: Battery?
)

data class Battery(
    @SerializedName("batteryId") val batteryId: String?,
    @SerializedName("voltage") val voltage: Double?,
    @SerializedName("current") val current: Double?,
    @SerializedName("temp") val temp: Double?,
    @SerializedName("soc") val soc: Double?,
    @SerializedName("userId") val userId: String?,
    @SerializedName("useYn") val useYn: String?
)

data class DealershipList(
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
