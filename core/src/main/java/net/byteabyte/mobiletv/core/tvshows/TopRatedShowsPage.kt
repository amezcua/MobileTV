package net.byteabyte.mobiletv.core.tvshows

data class TopRatedShowsPage(
    val currentPage: Int,
    val previousPage: Int?,
    val nextPage: Int?,
    val totalShowsCount: Int,
    val shows: List<Show>
)