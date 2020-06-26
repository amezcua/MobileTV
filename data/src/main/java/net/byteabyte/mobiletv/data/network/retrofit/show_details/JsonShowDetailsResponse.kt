package net.byteabyte.mobiletv.data.network.retrofit.show_details

import com.squareup.moshi.Json
import net.byteabyte.mobiletv.data.network.show_details.ShowDetailsNetwork

data class JsonShowDetailsResponse(
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "created_by") val authors: List<JsonShowAuthor>?,
    @Json(name = "id") val id: Int,
    @Json(name = "episode_run_time") val runTimes: List<Int>?,
    @Json(name = "first_air_date") val firstAired: String?, // TODO change to date
    @Json(name = "genres") val genres: List<JsonGenre>?,
    @Json(name = "homepage") val homePageUrl: String?,
    @Json(name = "in_production") val inProduction: Boolean?,
    @Json(name = "languages") val languages: List<String>?,
    @Json(name = "last_episode_to_air") val lastEpisode: JsonEpisode?,
    @Json(name = "name") val name: String,
    @Json(name = "next_episode_to_air") val nextEpisode: JsonEpisode?,
    @Json(name = "networks") val networks: List<JsonNetwork>?,
    @Json(name = "number_of_episodes") val episodesCount: Int?,
    @Json(name = "number_of_seasons") val seasonsCount: Int?,
    @Json(name = "origin_country") val originCountries: List<String>?,
    @Json(name = "original_name") val originalName: String?,
    @Json(name = "overview") val description: String?,
    @Json(name = "popularity") val popularity: Double?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "production_companies") val productionCompanies: List<JsonProductionCompany>?,
    @Json(name = "seasons") val seasons: List<JsonSeason>?,
    @Json(name = "status") val status: String?,
    @Json(name = "vote_average") val voteAverage: Double?,
    @Json(name = "vote_count") val totalVotes: Int?
) {
    fun toShowDetailsNetwork() = ShowDetailsNetwork(
        id = id,
        name = name,
        backdropPath = backdropPath.orEmpty(),
        authors = authors?.map { it.toShowAuthorNetwork() }.orEmpty(),
        runTimes = runTimes.orEmpty(),
        firstAirDate = firstAired.orEmpty(),
        genres = genres?.map { it.toGenreNetwork() }.orEmpty(),
        homePageUrl = homePageUrl.orEmpty(),
        inProduction = inProduction ?: false,
        languages = languages.orEmpty(),
        lastEpisode = lastEpisode?.toEpisodeNetwork(),
        nextEpisode = nextEpisode?.toEpisodeNetwork(),
        networks = networks?.map { it.toNetworkNetwork() }.orEmpty(),
        episodesCount = episodesCount ?: Int.MIN_VALUE,
        originCountries = originCountries.orEmpty(),
        originalName = originalName.orEmpty(),
        description = description.orEmpty(),
        popularity = popularity ?: Double.MIN_VALUE,
        posterPath = posterPath.orEmpty(),
        productionCompanies = productionCompanies?.map { it.toProductionCompanyNetwork() }.orEmpty(),
        seasons = seasons?.map { it.toSeasonNetwork() }.orEmpty(),
        status = status.orEmpty(),
        voteAverage = voteAverage ?: Double.MIN_VALUE,
        totalVotes = totalVotes ?: Int.MIN_VALUE
    )
}