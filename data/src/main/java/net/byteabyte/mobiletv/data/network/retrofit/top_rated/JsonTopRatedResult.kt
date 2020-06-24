package net.byteabyte.mobiletv.data.network.retrofit.top_rated

import com.squareup.moshi.Json
import net.byteabyte.mobiletv.data.network.retrofit.InvalidApiResponseException
import net.byteabyte.mobiletv.data.network.top_rated.TopRatedTvShow
import java.lang.Exception

data class JsonTopRatedResult(
    @Json(name = "id") val id: Int?,
    @Json(name = "name") val name: String?,
    @Json(name = "overview") val overview: String?,
    @Json(name = "popularity") val popularity: Double?,
    @Json(name = "vote_average") val voteAverage: Double?,
    @Json(name = "vote_count") val voteCount: Int?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "original_language") val originalLanguage: String?
) {
    // Only null checks are done here (captured in a layer above when parsing it instead of
    // letting the json serializer fail)
    fun toTopRatedShow(): TopRatedTvShow =
        try {
            TopRatedTvShow(
                id = this.id!!,
                name = this.name!!,
                description = this.overview ?: "",
                backdropImage = this.backdropPath ?: "",
                posterImage = this.posterPath ?: "",
                rating = this.voteAverage!!,
                totalVotes = this.voteCount!!
            )
        } catch (e: Exception) {
            throw InvalidApiResponseException(e, "Error parsing individual show. Id: $id. Name: $name")
        }
}