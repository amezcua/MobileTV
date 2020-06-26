package net.byteabyte.mobiletv.core.tvshows

interface ShowImagePicker {

    enum class Location {
        TOP_RATED_LIST_BG
    }

    fun pickBestImage(show: Show, location: Location, imageWidth: Pixels): PickImageResult

    sealed class PickImageResult {
        object Placeholder: PickImageResult()
        data class Image(val url: ImageUrl): PickImageResult()
    }
}