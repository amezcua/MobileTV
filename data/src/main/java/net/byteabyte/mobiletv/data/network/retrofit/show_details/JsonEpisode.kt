package net.byteabyte.mobiletv.data.network.retrofit.show_details

import com.squareup.moshi.Json
import net.byteabyte.mobiletv.data.network.show_details.EpisodeNetwork

data class JsonEpisode(
    @Json(name = "air_date") val airDate: String?, // TODO change to date
    @Json(name = "episode_number") val episodeNumber: Int?,
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "overview") val description: String?,
    @Json(name = "season_number") val seasonNumber: Int?,
    @Json(name = "show_id") val showId: Int,
    @Json(name = "still_path") val stillPath: String?,
    @Json(name = "vote_average") val voteAverage: Double?,
    @Json(name = "vote_count") val voteCount: Int?
) {
    fun toEpisodeNetwork() = EpisodeNetwork(
        airDate = airDate.orEmpty(),
        episodeNumber = episodeNumber ?: Int.MIN_VALUE,
        id = id,
        name = name,
        description = description.orEmpty(),
        seasonNumber = seasonNumber ?: Int.MIN_VALUE,
        showId = showId,
        stillPath = stillPath.orEmpty(),
        voteAverage = voteAverage ?: Double.MIN_VALUE,
        voteCount = voteCount ?: Int.MIN_VALUE
    )
}