package net.byteabyte.mobiletv.data

import net.byteabyte.mobiletv.core.Repository
import net.byteabyte.mobiletv.core.Repository.PagedResult
import net.byteabyte.mobiletv.core.Repository.RepositoryResult
import net.byteabyte.mobiletv.core.tvshows.ShowId
import net.byteabyte.mobiletv.core.tvshows.details.ShowDetails
import net.byteabyte.mobiletv.core.tvshows.top_rated.TopRatedShow
import net.byteabyte.mobiletv.data.network.TMDBNetwork
import net.byteabyte.mobiletv.data.network.TMDBNetworkResponse
import net.byteabyte.mobiletv.data.network.TMDBNetworkResponse.*
import net.byteabyte.mobiletv.data.network.imagesconfiguration.ImagesConfigurationNetwork
import java.lang.RuntimeException
import javax.inject.Inject

class MobileTVRepository @Inject constructor(private val tmdbNetwork: TMDBNetwork) : Repository {

    private var imagesConfigurationNetwork: ImagesConfigurationNetwork? = null

    override suspend fun getTopRated(pageNumber: Int): RepositoryResult<PagedResult<TopRatedShow>> {
        val configuration = cachedOrRequest()
        if (configuration !is Success<ImagesConfigurationNetwork>) return configurationError(configuration)

        return when (val topRated = tmdbNetwork.requestTopRated(pageNumber)) {
            is Success -> {
                val shows = topRated.data.shows.map { it.toShow(configuration.data) }
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
        val configuration = cachedOrRequest()
        if (configuration !is Success<ImagesConfigurationNetwork>) return configurationError(configuration)

        println("Invoked: Repository.getShowDetails $showId")

        return when (val tmdbNetworkResponse = tmdbNetwork.requestShowDetails(showId)) {
            is Unauthorised -> RepositoryResult.Error.AuthenticationError
            is ApiError -> RepositoryResult.Error.ServerError(tmdbNetworkResponse.error)
            is ServerError -> RepositoryResult.Error.ServerError(tmdbNetworkResponse.error)
            is Success -> RepositoryResult.Success(
                tmdbNetworkResponse.data.toShowDetails(configuration.data)
            )
        }
    }

    private suspend fun cachedOrRequest(): TMDBNetworkResponse<ImagesConfigurationNetwork> =
        imagesConfigurationNetwork.let {
            if (it != null) {
                Success(it)
            } else {
                tmdbNetwork.requestImagesConfiguration().apply {
                    if (this is Success) imagesConfigurationNetwork = this.data
                }
            }
        }

    private fun <T> configurationError(configurationResult: TMDBNetworkResponse<ImagesConfigurationNetwork>): RepositoryResult<T> =
        when (configurationResult) {
            is Unauthorised -> RepositoryResult.Error.AuthenticationError
            is ApiError -> RepositoryResult.Error.ServerError(configurationResult.error)
            is ServerError -> RepositoryResult.Error.ServerError(configurationResult.error)
            else -> RepositoryResult.Error.ServerError(RuntimeException("Unknown configuration error"))
        }
}