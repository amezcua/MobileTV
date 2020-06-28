package net.byteabyte.mobiletv.core.tvshows.paged

sealed class GetPagedSummariesResult {
    object Error : GetPagedSummariesResult()
    data class Success(val page: ShowSummariesPage) : GetPagedSummariesResult()
}