package net.byteabyte.mobiletv.core.tvshows

import net.byteabyte.mobiletv.core.tvshows.top_rated.TopRatedShow

interface ShowImagePicker {

    enum class Location {
        TOP_RATED_LIST_BG
    }

    fun pickBestImage(topRatedShow: TopRatedShow, location: Location, imageWidth: Pixels): PickImageResult

    sealed class PickImageResult {
        object Placeholder: PickImageResult()
        data class Image(val url: ImageUrl): PickImageResult()
    }
}