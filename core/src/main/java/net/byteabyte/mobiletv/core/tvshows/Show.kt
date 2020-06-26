package net.byteabyte.mobiletv.core.tvshows

typealias ShowId = Int
typealias ImageSizeKey = String
typealias ImageUrl = String
typealias ImagesMap = HashMap<ImageSizeKey, ImageUrl>
typealias Pixels = Int

data class Show(
    val id: ShowId,
    val name: String,
    val backdropImages: ImagesMap,
    val posterImages: ImagesMap,
    val description: String,
    val rating: Double,
    val totalVotes: Int
)