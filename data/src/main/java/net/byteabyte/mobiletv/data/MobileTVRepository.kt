package net.byteabyte.mobiletv.data

import net.byteabyte.mobiletv.core.Repository
import net.byteabyte.mobiletv.core.Repository.TopRatedResult
import net.byteabyte.mobiletv.core.tvshows.Show
import net.byteabyte.mobiletv.data.network.TMDBNetwork
import net.byteabyte.mobiletv.data.network.top_rated.TopRatedNetworkResponse
import net.byteabyte.mobiletv.data.network.top_rated.TopRatedNetworkResponse.*
import java.lang.Exception
import javax.inject.Inject

class MobileTVRepository @Inject constructor(private val tmdbNetwork: TMDBNetwork): Repository {

    override suspend fun getTopRated(): TopRatedResult {
        // TODO combine with the TMDB configuration to have full image urls here
        return when (val result = tmdbNetwork.requestTopRated()) {
            is Success -> {
                val shows = result.topRatedResponsePage.shows.map { it.toShow() }
                TopRatedResult.Success(shows)
            }
            is Unauthorised -> TopRatedResult.Error(Exception("Unauthenticated"))
            is ApiError -> TopRatedResult.Error(result.error)
            is ServerError -> TopRatedResult.Error(result.error)
        }
    }
}