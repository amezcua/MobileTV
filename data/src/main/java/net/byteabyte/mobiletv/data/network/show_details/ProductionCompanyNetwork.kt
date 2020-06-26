package net.byteabyte.mobiletv.data.network.show_details

import net.byteabyte.mobiletv.core.tvshows.details.ProductionCompany
import net.byteabyte.mobiletv.data.network.imagesconfiguration.ImagesConfigurationNetwork

data class ProductionCompanyNetwork(
    val id: Int,
    val logoPath: String,
    val name: String,
    val originCountry: String
) {
    fun toProductionCompany(imagesConfiguration: ImagesConfigurationNetwork) = ProductionCompany(
        id = id,
        logos = imagesConfiguration.buildLogoImages(logoPath),
        name = name,
        originCountry = originCountry
    )
}