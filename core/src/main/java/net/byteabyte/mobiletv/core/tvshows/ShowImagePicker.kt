package net.byteabyte.mobiletv.core.tvshows

interface ShowImagePicker {

    fun pickBestImage(imageMaps: List<ImagesMap>, imageWidth: Pixels): PickImageResult

    sealed class PickImageResult {
        object Placeholder: PickImageResult()
        data class Image(val url: ImageUrl): PickImageResult()
    }
}