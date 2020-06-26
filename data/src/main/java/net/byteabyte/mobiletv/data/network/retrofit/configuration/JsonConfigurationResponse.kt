package net.byteabyte.mobiletv.data.network.retrofit.configuration

import com.squareup.moshi.Json
import net.byteabyte.mobiletv.data.network.imagesconfiguration.ImagesConfigurationNetwork

data class JsonConfigurationResponse(
    @Json(name = "images") val images: JsonImagesConfiguration
) {
    fun toConfigurationNetworkResponse() =
        ImagesConfigurationNetwork(
            baseUrl = images.secureBaseUrl,
            backDropSizes = images.backDropSizes,
            posterSizes = images.posterSizes,
            profileSizes = images.profileSizes,
            logoSizes = images.logoSizes,
            stillSizes = images.stillSizes
        )
}