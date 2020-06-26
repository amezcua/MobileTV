package net.byteabyte.mobiletv.core.tvshows.top_rated

import net.byteabyte.mobiletv.core.tvshows.top_rated.TopRatedShow

data class TopRatedShowsPage(
    val currentPage: Int,
    val previousPage: Int?,
    val nextPage: Int?,
    val totalShowsCount: Int,
    val topRatedShows: List<TopRatedShow>
)