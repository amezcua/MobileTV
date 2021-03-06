package net.byteabyte.mobiletv.data.network.retrofit

import net.byteabyte.mobiletv.data.network.retrofit.configuration.JsonConfigurationResponse
import net.byteabyte.mobiletv.data.network.retrofit.show_details.JsonShowDetailsResponse
import net.byteabyte.mobiletv.data.network.retrofit.paged_shows.JsonPagedShowsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {
    @GET("/3/tv/top_rated")
    suspend fun topRatedTvShows(
        @Query("api_key") apiKey: String,
        @Query("page") pageNumber: Int
    ): JsonPagedShowsResponse

    @GET("/3/configuration")
    suspend fun configuration(
        @Query("api_key") apiKey: String
    ): JsonConfigurationResponse

    @GET("/3/tv/{show_id}")
    suspend fun showDetails(
        @Path("show_id") showId: Int,
        @Query("api_key") apiKey: String
    ): JsonShowDetailsResponse

    @GET("/3/tv/{show_id}/similar")
    suspend fun similarShows(
        @Path("show_id") showId: Int,
        @Query("api_key") apiKey: String,
        @Query("page") pageNumber: Int
    ): JsonPagedShowsResponse
}