package net.byteabyte.mobiletv.core.tvshows

typealias ShowId = Int
typealias ImageSize = String
typealias ImageUrl = String

data class Show(
    val id: ShowId,
    val name: String,
    val backdropImages: HashMap<ImageSize, ImageUrl>,
    val posterImages: HashMap<ImageSize, ImageUrl>,
    val description: String,
    val rating: Double,
    val totalVotes: Int
)