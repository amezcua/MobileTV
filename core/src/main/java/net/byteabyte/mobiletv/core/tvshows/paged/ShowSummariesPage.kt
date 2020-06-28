package net.byteabyte.mobiletv.core.tvshows.paged

data class ShowSummariesPage(
    val currentPage: Int,
    val previousPage: Int?,
    val nextPage: Int?,
    val totalShowsCount: Int,
    val shows: List<ShowSummary>
)