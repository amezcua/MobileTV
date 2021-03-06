package net.byteabyte.mobiletv.data.network

import net.byteabyte.mobiletv.data.network.TMDBNetworkResponse.*
import net.byteabyte.mobiletv.data.network.imagesconfiguration.ImagesConfigurationNetwork
import net.byteabyte.mobiletv.data.network.retrofit.InvalidApiResponseException
import net.byteabyte.mobiletv.data.network.retrofit.TMDBApi
import net.byteabyte.mobiletv.data.network.show_details.ShowDetailsNetwork
import net.byteabyte.mobiletv.data.network.paged_shows.NetworkShowsPage
import retrofit2.HttpException
import java.lang.RuntimeException

class TMDBNetwork(private val apiKey: String, private val tmdbApi: TMDBApi) {
    internal suspend fun requestTopRated(pageNumber: Int): TMDBNetworkResponse<NetworkShowsPage> =
        try {
            Success(
                tmdbApi.topRatedTvShows(apiKey, pageNumber).toNetworkResponsePage()
            )
        } catch (e: HttpException) {
            buildErrorForHttpException(e)
        } catch (e: Exception) {
            ServerError(e)
        }

    internal suspend fun requestSimilarShows(pageNumber: Int, showId: Int): TMDBNetworkResponse<NetworkShowsPage> =
        try {
            Success(
                tmdbApi.similarShows(showId, apiKey, pageNumber).toNetworkResponsePage()
            )
        } catch (e: HttpException) {
            buildErrorForHttpException(e)
        } catch (e: Exception) {
            ServerError(e)
        }

    internal suspend fun requestImagesConfiguration(): TMDBNetworkResponse<ImagesConfigurationNetwork> =
        try {
            Success(tmdbApi.configuration(apiKey).toConfigurationNetworkResponse())
        } catch (e: HttpException) {
            buildErrorForHttpException(e)
        } catch (e: Exception) {
            ServerError(e)
        }

    internal suspend fun requestShowDetails(showId: Int): TMDBNetworkResponse<ShowDetailsNetwork> =
        try {
            val detailsNetwork = tmdbApi.showDetails(showId, apiKey).toShowDetailsNetwork()
            if (detailsNetwork.isValid()) {
                Success(detailsNetwork)
            } else {
                ApiError(InvalidApiResponseException(RuntimeException(), "The show data received for id: $showId is not valid"))
            }
        } catch (e: HttpException) {
            buildErrorForHttpException(e)
        } catch (e: Exception) {
            ServerError(e)
        }

    private fun buildErrorForHttpException(e: HttpException): TMDBNetworkResponse<Nothing> =
        when (e.code()) {
            400 -> ApiError(e)
            401 -> Unauthorised
            500 -> ServerError(e)
            else -> ApiError(e)
        }
}