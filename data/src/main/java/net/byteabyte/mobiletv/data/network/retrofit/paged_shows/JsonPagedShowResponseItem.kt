package net.byteabyte.mobiletv.data.network.retrofit.paged_shows

import com.squareup.moshi.Json
import net.byteabyte.mobiletv.data.network.retrofit.InvalidApiResponseException
import net.byteabyte.mobiletv.data.network.paged_shows.PagedNetworkShow
import java.lang.Exception

data class JsonPagedShowResponseItem(
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
    internal fun toTopRatedShow(): PagedNetworkShow =
        try {
            PagedNetworkShow(
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