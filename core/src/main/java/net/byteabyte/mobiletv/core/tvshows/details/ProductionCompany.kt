package net.byteabyte.mobiletv.core.tvshows.details

import net.byteabyte.mobiletv.core.tvshows.ImagesMap
import net.byteabyte.mobiletv.core.tvshows.ProductionCompanyId

data class ProductionCompany(
    val id: ProductionCompanyId,
    val logos: ImagesMap,
    val name: String,
    val originCountry: String
)