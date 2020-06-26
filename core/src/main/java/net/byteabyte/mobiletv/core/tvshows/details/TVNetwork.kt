package net.byteabyte.mobiletv.core.tvshows.details

import net.byteabyte.mobiletv.core.tvshows.ImagesMap
import net.byteabyte.mobiletv.core.tvshows.TVNetworkId

data class TVNetwork(
    val id: TVNetworkId,
    val name: String,
    val logos: ImagesMap
)