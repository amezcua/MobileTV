package net.byteabyte.mobiletv.data.network

import net.byteabyte.mobiletv.data.network.retrofit.TMDBApi
import net.byteabyte.mobiletv.data.network.top_rated.TopRatedNetworkResponse
import net.byteabyte.mobiletv.data.network.top_rated.TopRatedNetworkResponse.*
import retrofit2.HttpException

class TMDBNetwork(private val apiKey: String, private val tmdbApi: TMDBApi) {
    suspend fun requestTopRated(): TopRatedNetworkResponse {
        return try {
            val page = tmdbApi.topRatedTvShows(apiKey).toNetworkResponsePage()
            Success(page)
        } catch (e: HttpException) {
            when (e.code()) {
                400 -> ApiError(e)
                401 -> Unauthorised
                500 -> ServerError(e)
                else -> ApiError(e)
            }
        } catch (e: Exception) {
            ServerError(e)
        }
    }
}