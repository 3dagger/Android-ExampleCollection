package com.dagger.custommarkerview.di

import com.google.gson.annotations.SerializedName

data class Station(
    @SerializedName("_embedded") var embedded: Embedded,
    @SerializedName("_links") var links: Links,
    @SerializedName("_page") var page: Page)

data class Embedded(
    @SerializedName("stationList") var stationList: List<EntiretyStationList>
)

data class EntiretyStationList(
    @SerializedName("stationId") var stationId: String,
    @SerializedName("parentId") var parentId: String?,
    @SerializedName("status") var status: String?,
    @SerializedName("lat") var lat: Double,
    @SerializedName("lon") var lon: Double,
    @SerializedName("address1") var address1: String?,
    @SerializedName("address2") var address2: String?,
    @SerializedName("slots") var slots: List<Slots>?,
    @SerializedName("socCount") var socCount: Int?,
    @SerializedName("maxSoc") var maxSoc: Double?,
    @SerializedName("imageUri") var imageUri: String?,
    @SerializedName("bookmark") var bookmark: Boolean?,
    @SerializedName("connYn") var connYn: Boolean
)

data class Slots(
    @SerializedName("slotId") var slotId: SlotId?,
    @SerializedName("batteryId") var batteryId: String,
    @SerializedName("errorCode") var errorCode: String,
    @SerializedName("battery") var battery: Battery?
)

data class SlotId(
    @SerializedName("stationId") var stationId: String,
    @SerializedName("slot") var slot: String
)

data class Battery(
    @SerializedName("batteryId") var batteryId: String?,
    @SerializedName("voltage") var voltage: Double?,
    @SerializedName("current") var current: Double?,
    @SerializedName("temp") var temp: Double?,
    @SerializedName("soc") var soc: Double?,
    @SerializedName("userId") var userId: String?,
    @SerializedName("useYn") var useYn: String?
)

data class Links(
    @SerializedName("self") var self: Self,
    @SerializedName("profile") var profile: Profile
)

data class Self(@SerializedName("href") var href: String)
data class Profile(@SerializedName("href") var href: String)
data class Page(
    @SerializedName("size") var size: Int,
    @SerializedName("totalElements") var totalElements: Int,
    @SerializedName("totalPages") var totalPages: Int,
    @SerializedName("number") var number: Int
)
