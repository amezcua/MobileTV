package net.byteabyte.mobiletv.data.network.show_details

import net.byteabyte.mobiletv.core.tvshows.details.TVNetwork
import net.byteabyte.mobiletv.data.network.imagesconfiguration.ImagesConfigurationNetwork

data class NetworkNetwork(
    val id: Int,
    val name: String,
    val logoPath: String,
    val originCountry: String
) {
    fun toTVNetwork(imagesConfiguration: ImagesConfigurationNetwork) = TVNetwork(
        id = id,
        name = name,
        logos = imagesConfiguration.buildLogoImages(logoPath)
    )
}