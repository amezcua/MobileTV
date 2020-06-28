package net.byteabyte.mobiletv.core.tvshows

import net.byteabyte.mobiletv.core.tvshows.ShowImagePicker.PickImageResult
import net.byteabyte.mobiletv.core.tvshows.top_rated.TopRatedShow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class ShowImagePickerImplTest {

    @Test
    fun `For a show with no images return use placeholder`() {
        val show = aShow()
        val showImagePicker = ShowImagePickerImpl()

        val best = showImagePicker.pickBestImage(listOf(show.backdropImages, show.posterImages) , 10)

        assertEquals(PickImageResult.Placeholder, best)
    }

    @Test
    fun `In there is only one poster pick it`() {
        val posterUrl = "posterUrl"
        val show = aShow(posterImages = hashMapOf("default" to posterUrl))
        val showImagePicker = ShowImagePickerImpl()

        val best = showImagePicker.pickBestImage(listOf(show.backdropImages, show.posterImages) , 10)

        assertEquals(PickImageResult.Image(posterUrl), best)
    }

    @Test
    fun `In there is only one backdrop pick it`() {
        val backdropUrl = "backdropUrl"
        val show = aShow(backdropImages = hashMapOf("default" to backdropUrl))
        val showImagePicker = ShowImagePickerImpl()

        val best = showImagePicker.pickBestImage(listOf(show.backdropImages, show.posterImages) , 10)

        assertEquals(PickImageResult.Image(backdropUrl), best)
    }

    @Test
    fun `When multiple sizes pick the closest one`() {
        val show = aShow(backdropImages = hashMapOf(
            "w92" to "92url",
            "w185" to "185url",
            "w500" to "500url",
            "w780" to "780url",
            "w154" to "154url"
        ))
        val showImagePicker = ShowImagePickerImpl()

        val best92 = showImagePicker.pickBestImage(listOf(show.backdropImages), 90) as PickImageResult.Image
        val best195 = showImagePicker.pickBestImage(listOf(show.backdropImages), 195) as PickImageResult.Image
        val best490 = showImagePicker.pickBestImage(listOf(show.backdropImages), 490) as PickImageResult.Image
        val best1280 = showImagePicker.pickBestImage(listOf(show.backdropImages), 1280) as PickImageResult.Image
        val best150 = showImagePicker.pickBestImage(listOf(show.backdropImages), 150) as PickImageResult.Image
        val best48 = showImagePicker.pickBestImage(listOf(show.backdropImages), 48) as PickImageResult.Image

        assertEquals("92url", best92.url)
        assertEquals("185url", best195.url)
        assertEquals("500url", best490.url)
        assertEquals("780url", best1280.url)
        assertEquals("154url", best150.url)
        assertEquals("92url", best48.url)
    }

    private fun aShow(
        backdropImages: ImagesMap = hashMapOf(),
        posterImages: ImagesMap = hashMapOf()
    ) = TopRatedShow(
        id = Random().nextInt(1000),
        name = "aName",
        description = "aDescription",
        rating = Random().nextDouble(),
        totalVotes = Random().nextInt(10),
        backdropImages = backdropImages,
        posterImages = posterImages
    )
}