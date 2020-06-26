package net.byteabyte.mobiletv.data

import net.byteabyte.mobiletv.core.Repository
import net.byteabyte.mobiletv.core.Repository.*
import net.byteabyte.mobiletv.core.tvshows.ShowId
import net.byteabyte.mobiletv.core.tvshows.details.ShowDetails
import net.byteabyte.mobiletv.core.tvshows.top_rated.TopRatedShow
import net.byteabyte.mobiletv.data.network.TMDBNetwork
import net.byteabyte.mobiletv.data.network.TMDBNetworkResponse.*
import net.byteabyte.mobiletv.data.network.imagesconfiguration.ImagesConfigurationNetwork
import javax.inject.Inject

class MobileTVRepository @Inject constructor(private val tmdbNetwork: TMDBNetwork): Repository {

    private var imagesConfigurationNetwork: ImagesConfigurationNetwork? = null

    override suspend fun getTopRated(pageNumber: Int): RepositoryResult<PagedResult<TopRatedShow>> {
        if(imagesConfigurationNetwork == null) {
            when (val response = tmdbNetwork.requestImagesConfiguration()) {
                Unauthorised -> return RepositoryResult.Error.AuthenticationError
                is ApiError -> return RepositoryResult.Error.ServerError(response.error)
                is ServerError -> return RepositoryResult.Error.ServerError(response.error)
                is Success -> imagesConfigurationNetwork = response.data
            }
        }

        return when (val topRated = tmdbNetwork.requestTopRated(pageNumber)) {
            is Success -> {
                val shows = topRated.data.shows.map { it.toShow(imagesConfigurationNetwork!!) }
                val page = topRated.data.page
                val showsCount = topRated.data.totalResults
                val pagesCount = topRated.data.totalPages
                RepositoryResult.Success(PagedResult(page, showsCount, pagesCount, shows))
            }
            is Unauthorised -> RepositoryResult.Error.AuthenticationError
            is ApiError -> RepositoryResult.Error.ServerError(topRated.error)
            is ServerError -> RepositoryResult.Error.ServerError(topRated.error)
        }
    }

    override suspend fun getShowDetails(showId: ShowId): RepositoryResult<ShowDetails> {
        if(imagesConfigurationNetwork == null) {
            when (val response = tmdbNetwork.requestImagesConfiguration()) {
                is Unauthorised -> return RepositoryResult.Error.AuthenticationError
                is ApiError -> return RepositoryResult.Error.ServerError(response.error)
                is ServerError -> return RepositoryResult.Error.ServerError(response.error)
                is Success -> imagesConfigurationNetwork = response.data
            }
        }

        return when (val tmdbNetworkResponse = tmdbNetwork.requestShowDetails(showId)) {
            is Unauthorised -> RepositoryResult.Error.AuthenticationError
            is ApiError -> RepositoryResult.Error.ServerError(tmdbNetworkResponse.error)
            is ServerError -> RepositoryResult.Error.ServerError(tmdbNetworkResponse.error)
            is Success -> RepositoryResult.Success(tmdbNetworkResponse.data.toShowDetails(imagesConfigurationNetwork!!))
        }
    }
}