package net.byteabyte.mobiletv.data.network.imagesconfiguration

import net.byteabyte.mobiletv.core.tvshows.ImageSizeKey
import net.byteabyte.mobiletv.core.tvshows.ImageUrl
import net.byteabyte.mobiletv.core.tvshows.ImagesMap

data class ImagesConfigurationNetwork(
    val baseUrl: String,
    val backDropSizes: List<ImageSizeKey>,
    val posterSizes: List<ImageSizeKey>,
    val profileSizes: List<ImageSizeKey>,
    val logoSizes: List<ImageSizeKey>,
    val stillSizes: List<ImageSizeKey>
) {
    fun buildBackdropImages(backdropUrlPart: String): ImagesMap = buildImageUrlsList(
        baseUrl,
        backDropSizes,
        backdropUrlPart
    )

    fun buildPosterImages(posterUrlPart: String): ImagesMap = buildImageUrlsList(
        baseUrl,
        posterSizes,
        posterUrlPart
    )

    fun buildProfileImages(profileImagePart: String): ImagesMap = buildImageUrlsList(
        baseUrl,
        profileSizes,
        profileImagePart
    )

    fun buildLogoImages(logoImagePart: String): ImagesMap = buildImageUrlsList(
        baseUrl,
        logoSizes,
        logoImagePart
    )

    fun buildStillImages(stillImagePart: String): ImagesMap = buildImageUrlsList(
        baseUrl,
        stillSizes,
        stillImagePart
    )

    private fun buildImageUrlsList(
        baseUrl: String,
        sizes: List<ImageSizeKey>,
        imageUrlPart: String
    ): ImagesMap {
        return if (imageUrlPart.isBlank()) {
            hashMapOf()
        } else {
            val imagesList = hashMapOf<ImageSizeKey, ImageUrl>()
            sizes.forEach { size ->
                imagesList[size] = "$baseUrl$size$imageUrlPart"
            }
            imagesList
        }
    }
}