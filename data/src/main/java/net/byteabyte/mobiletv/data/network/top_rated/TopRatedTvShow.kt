package net.byteabyte.mobiletv.data.network.top_rated

import net.byteabyte.mobiletv.core.tvshows.Show
import net.byteabyte.mobiletv.data.network.imagesconfiguration.ImagesConfigurationNetwork

internal data class TopRatedTvShow(
    val id: Int,
    val name: String,
    val backdropImage: String,
    val posterImage: String,
    val description: String,
    val rating: Double,
    val totalVotes: Int
) {
    fun toShow(imagesConfiguration: ImagesConfigurationNetwork): Show {
        return Show(
            id = id,
            name = name,
            backdropImages = buildImageUrlsList(
                imagesConfiguration.baseUrl,
                imagesConfiguration.backDropSizes,
                backdropImage
            ),
            posterImages = buildImageUrlsList(
                imagesConfiguration.baseUrl,
                imagesConfiguration.posterSizes,
                posterImage
            ),
            description = description,
            rating = rating,
            totalVotes = totalVotes
        )
    }

    private fun buildImageUrlsList(
        baseUrl: String,
        sizes: List<String>,
        imageUrlPart: String
    ): HashMap<String, String> {
        return if (imageUrlPart.isBlank()) {
            hashMapOf()
        } else {
            val imagesList = hashMapOf<String, String>()
            sizes.forEach { size ->
                imagesList[size] = "$baseUrl$size$imageUrlPart"
            }
            imagesList
        }
    }
}