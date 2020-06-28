package net.byteabyte.mobiletv.core

import net.byteabyte.mobiletv.core.tvshows.ShowId
import net.byteabyte.mobiletv.core.tvshows.details.ShowDetails
import net.byteabyte.mobiletv.core.tvshows.paged.ShowSummary

interface Repository {
    suspend fun getTopRated(pageNumber: Int): RepositoryResult<PagedResult<ShowSummary>>
    suspend fun getShowDetails(showId: ShowId): RepositoryResult<ShowDetails>
    suspend fun getSimilarShows(showId: ShowId, pageNumber: Int): RepositoryResult<PagedResult<ShowSummary>>

    data class PagedResult<T>(
        val page: Int,
        val showsCount: Int,
        val pagesCount: Int,
        val results: List<T>
    )

    sealed class RepositoryResult<out T> {
        data class Success<T>(val data: T): RepositoryResult<T>()
        sealed class Error : RepositoryResult<Nothing>() {
            object AuthenticationError: Error()
            data class ServerError(val source: Exception): Error()
        }
    }
}