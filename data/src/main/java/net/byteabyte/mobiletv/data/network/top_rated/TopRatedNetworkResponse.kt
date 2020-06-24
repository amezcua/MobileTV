package net.byteabyte.mobiletv.data.network.top_rated

sealed class TopRatedNetworkResponse {
    object Unauthorised: TopRatedNetworkResponse()
    data class ApiError(val error: Exception): TopRatedNetworkResponse()
    data class ServerError(val error: Exception): TopRatedNetworkResponse()
    class Success(val topRatedResponsePage: TopRatedResponsePage): TopRatedNetworkResponse()
}