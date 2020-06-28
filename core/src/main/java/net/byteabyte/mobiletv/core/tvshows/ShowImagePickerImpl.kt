package net.byteabyte.mobiletv.core.tvshows

import net.byteabyte.mobiletv.core.tvshows.ShowImagePicker.PickImageResult
import net.byteabyte.mobiletv.core.tvshows.ShowImagePicker.PickImageResult.Image
import net.byteabyte.mobiletv.core.tvshows.ShowImagePicker.PickImageResult.Placeholder
import kotlin.math.abs

internal class ShowImagePickerImpl : ShowImagePicker {
    override fun pickBestImage(
        imageMaps: List<ImagesMap>,
        imageWidth: Pixels
    ): PickImageResult {
        imageMaps.forEach {
            val picked = pickBestFrom(it, imageWidth)
            if (picked != Placeholder) {
                return picked
            }
        }
        return Placeholder
    }

    private fun pickBestFrom(imagesMap: ImagesMap, imageWidth: Pixels): PickImageResult =
        if (imagesMap.isEmpty()) {
            Placeholder
        } else {
            val defaultImage = imagesMap["default"]
            val closestSize = imagesMap.keys.closestTo(imageWidth)
            val closestUrl = imagesMap[closestSize]

            when {
                (closestUrl != null) -> Image(closestUrl)
                (defaultImage != null) -> Image(defaultImage)
                else -> Placeholder
            }
        }

    private fun Set<ImageSizeKey>.closestTo(value: Int): ImageSizeKey? {
        return map { Pair(it, it.substring(1).toIntOrNull()) }
            .filter { it.second != null }
            .map { Pair(it.first, it.second!!) }
            .minBy { abs(value - it.second) }?.first
    }
}