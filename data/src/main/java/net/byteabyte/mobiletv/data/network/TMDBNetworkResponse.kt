package net.byteabyte.mobiletv.data.network

sealed class TMDBNetworkResponse<out T> {
    object Unauthorised: TMDBNetworkResponse<Nothing>()
    data class ApiError(val error: Exception): TMDBNetworkResponse<Nothing>()
    data class ServerError(val error: Exception): TMDBNetworkResponse<Nothing>()
    class Success<T>(val response: T): TMDBNetworkResponse<T>()
}