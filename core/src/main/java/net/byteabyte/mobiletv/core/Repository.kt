package net.byteabyte.mobiletv.core

import net.byteabyte.mobiletv.core.tvshows.Show
import java.lang.Exception

interface Repository {
    suspend fun getTopRated(pageNumber: Int): TopRatedResult

    sealed class TopRatedResult {
        data class Success(
            val page: Int,
            val showsCount: Int,
            val pagesCount: Int,
            val results: List<Show>
        ) : TopRatedResult()

        data class Error(val error: Exception) : TopRatedResult()
    }
}