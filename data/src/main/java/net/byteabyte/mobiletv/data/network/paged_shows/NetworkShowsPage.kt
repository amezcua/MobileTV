package net.byteabyte.mobiletv.data.network.paged_shows

internal data class NetworkShowsPage(
    val totalPages: Int,
    val totalResults: Int,
    val page: Int,
    val shows: List<PagedNetworkShow>
)