package net.byteabyte.mobiletv.data.network.retrofit

import net.byteabyte.mobiletv.data.network.retrofit.top_rated.JsonTopRatedShowsResult
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApi {
    @GET("/3/tv/top_rated")
    suspend fun topRatedTvShows(
        @Query("api_key") apiKey: String,
        @Query("page") pageNumber: Int
    ): JsonTopRatedShowsResult
}