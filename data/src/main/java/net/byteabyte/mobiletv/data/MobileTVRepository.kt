package net.byteabyte.mobiletv.data

import net.byteabyte.mobiletv.core.Repository
import net.byteabyte.mobiletv.core.Repository.TopRatedResult
import net.byteabyte.mobiletv.data.network.TMDBNetwork
import net.byteabyte.mobiletv.data.network.TMDBNetworkResponse.*
import net.byteabyte.mobiletv.data.network.imagesconfiguration.ImagesConfigurationNetwork
import javax.inject.Inject

class MobileTVRepository @Inject constructor(private val tmdbNetwork: TMDBNetwork): Repository {

    private var imagesConfigurationNetwork: ImagesConfigurationNetwork? = null

    override suspend fun getTopRated(pageNumber: Int): TopRatedResult {
        if(imagesConfigurationNetwork == null) {
            when (val response = tmdbNetwork.requestImagesConfiguration()) {
                Unauthorised -> return TopRatedResult.Error.AuthenticationError
                is ApiError -> return TopRatedResult.Error.ServerError(response.error)
                is ServerError -> return TopRatedResult.Error.ServerError(response.error)
                is Success -> imagesConfigurationNetwork = response.response
            }
        }

        return when (val result = tmdbNetwork.requestTopRated(pageNumber)) {
            is Success -> {
                val shows = result.response.shows.map { it.toShow(imagesConfigurationNetwork!!) }
                val page = result.response.page
                val showsCount = result.response.totalResults
                val pagesCount = result.response.totalPages
                TopRatedResult.Success(page, showsCount, pagesCount, shows)
            }
            is Unauthorised -> TopRatedResult.Error.AuthenticationError
            is ApiError -> TopRatedResult.Error.ServerError(result.error)
            is ServerError -> TopRatedResult.Error.ServerError(result.error)
        }
    }
}