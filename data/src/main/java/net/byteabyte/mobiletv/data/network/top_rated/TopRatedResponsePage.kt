package net.byteabyte.mobiletv.data.network.top_rated

data class TopRatedResponsePage(
    val totalPages: Int,
    val totalResults: Int,
    val page: Int,
    val shows: List<TopRatedTvShow>
)