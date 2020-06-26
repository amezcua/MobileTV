package net.byteabyte.mobiletv.data.network.retrofit.show_details

import com.squareup.moshi.Json
import net.byteabyte.mobiletv.data.network.show_details.SeasonNetwork

data class JsonSeason(
    @Json(name = "air_date") val airDate: String?, // TODO change to date
    @Json(name = "episode_count") val episodesCount: Int?,
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String?,
    @Json(name = "overview") val description: String?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "season_number") val seasonNumber: Int?
) {
    fun toSeasonNetwork() = SeasonNetwork(
        airDate = airDate.orEmpty(),
        episodesCount = episodesCount ?: Int.MIN_VALUE,
        id = id,
        name = name.orEmpty(),
        description = description.orEmpty(),
        posterPath = posterPath.orEmpty(),
        seasonNumber = seasonNumber ?: Int.MIN_VALUE
    )
}