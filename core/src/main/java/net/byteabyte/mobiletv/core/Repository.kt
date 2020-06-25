package net.byteabyte.mobiletv.core

import net.byteabyte.mobiletv.core.tvshows.Show

interface Repository {
    suspend fun getTopRated(pageNumber: Int): TopRatedResult

    sealed class TopRatedResult {
        data class Success(
            val page: Int,
            val showsCount: Int,
            val pagesCount: Int,
            val results: List<Show>
        ) : TopRatedResult()

        sealed class Error : TopRatedResult() {
            object AuthenticationError: Error()
            data class ServerError(val source: Exception): Error()
        }
    }
}