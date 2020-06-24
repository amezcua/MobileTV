package net.byteabyte.mobiletv.data

import net.byteabyte.mobiletv.core.Repository
import net.byteabyte.mobiletv.core.Repository.TopRatedResult
import net.byteabyte.mobiletv.data.network.TMDBNetwork
import net.byteabyte.mobiletv.data.network.top_rated.TopRatedNetworkResponse.*
import javax.inject.Inject

class MobileTVRepository @Inject constructor(private val tmdbNetwork: TMDBNetwork): Repository {

    override suspend fun getTopRated(pageNumber: Int): TopRatedResult {
        // TODO combine with the TMDB configuration to have full image urls here
        return when (val result = tmdbNetwork.requestTopRated(pageNumber)) {
            is Success -> {
                val shows = result.topRatedResponsePage.shows.map { it.toShow() }
                val page = result.topRatedResponsePage.page
                val showsCount = result.topRatedResponsePage.totalResults
                val pagesCount = result.topRatedResponsePage.totalPages
                TopRatedResult.Success(page, showsCount, pagesCount, shows)
            }
            is Unauthorised -> TopRatedResult.Error(Exception("Unauthenticated"))
            is ApiError -> TopRatedResult.Error(result.error)
            is ServerError -> TopRatedResult.Error(result.error)
        }
    }
}