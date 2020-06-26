package net.byteabyte.mobiletv.data.network.retrofit.show_details

import com.squareup.moshi.Json
import net.byteabyte.mobiletv.data.network.show_details.NetworkNetwork

data class JsonNetwork(
    @Json(name = "name") val name: String,
    @Json(name = "id") val id: Int,
    @Json(name = "logo_path") val logoPath: String?,
    @Json(name = "origin_country") val originCountry: String?
) {
    fun toNetworkNetwork() = NetworkNetwork(
        id = id,
        name = name,
        logoPath = logoPath.orEmpty(),
        originCountry = originCountry.orEmpty()
    )
}