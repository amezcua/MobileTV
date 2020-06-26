package net.byteabyte.mobiletv.data.network.show_details

import net.byteabyte.mobiletv.core.tvshows.details.Author
import net.byteabyte.mobiletv.data.network.imagesconfiguration.ImagesConfigurationNetwork

data class ShowAuthorNetwork(
    val id: Int,
    val name: String,
    val profileImage: String
) {
    fun toAuthor(imagesConfiguration: ImagesConfigurationNetwork) = Author(
        id = id,
        name = name,
        profileImages = imagesConfiguration.buildProfileImages(profileImage)
    )
}