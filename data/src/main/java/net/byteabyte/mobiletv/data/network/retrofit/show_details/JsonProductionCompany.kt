package net.byteabyte.mobiletv.data.network.retrofit.show_details

import com.squareup.moshi.Json
import net.byteabyte.mobiletv.data.network.show_details.ProductionCompanyNetwork

data class JsonProductionCompany(
    @Json(name = "id") val id: Int,
    @Json(name = "logo_path") val logoPath: String?,
    @Json(name = "name") val name: String,
    @Json(name = "origin_country") val originCountry: String?
) {
    fun toProductionCompanyNetwork() = ProductionCompanyNetwork(
        id = id,
        logoPath = logoPath.orEmpty(),
        name = name,
        originCountry = originCountry.orEmpty()
    )
}