package net.byteabyte.mobiletv.core.tvshows

data class Show(
    val id: ShowId,
    val name: String,
    val backdropImage: String,
    val posterImage: String,
    val description: String,
    val rating: Double,
    val totalVotes: Int
)