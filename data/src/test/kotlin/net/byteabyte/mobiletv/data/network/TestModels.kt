package net.byteabyte.mobiletv.data.network

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import net.byteabyte.mobiletv.data.network.imagesconfiguration.ImagesConfigurationNetwork
import net.byteabyte.mobiletv.data.network.retrofit.TMDBApi
import net.byteabyte.mobiletv.data.network.retrofit.configuration.JsonConfigurationResponse
import net.byteabyte.mobiletv.data.network.retrofit.configuration.JsonImagesConfiguration
import net.byteabyte.mobiletv.data.network.show_details.*
import net.byteabyte.mobiletv.data.network.top_rated.TopRatedResponsePage
import net.byteabyte.mobiletv.data.network.top_rated.TopRatedNetworkShow
import retrofit2.HttpException
import java.util.*

internal fun buildTmdbNetwork(tmdbApi: TMDBApi) = TMDBNetwork("anApiKey", tmdbApi)

internal fun a400Error(): HttpException = anHttpError(400)
internal fun a401Error(): HttpException = anHttpError(401)
internal fun a500Error(): HttpException = anHttpError(500)

private fun anHttpError(code: Int): HttpException =
    mock<HttpException>().apply { whenever(this.code()).thenReturn(code) }

internal fun aTopRatedResponsePage() = TopRatedResponsePage(
    page = 1,
    totalPages = 10,
    totalResults = 100,
    shows = (0 until 10).map {
        aTopRatedShow(it)
    }
)

private fun aTopRatedShow(id: Int) = TopRatedNetworkShow(
    id = id,
    totalVotes = Random().nextInt(),
    rating = Random().nextDouble(),
    posterImage = "aPosterImage",
    backdropImage = "aBackdropImage",
    description = "aDescription",
    name = "aName"
)

fun aValidImagesConfiguration(): ImagesConfigurationNetwork =
    aValidJsonConfigurationResult().toConfigurationNetworkResponse()

fun aValidJsonConfigurationResult() = JsonConfigurationResponse(
        images = aValidJsonImagesConfiguration()
    )

private fun aValidJsonImagesConfiguration() = JsonImagesConfiguration(
        baseUrl = "aBaseUrl",
        secureBaseUrl = "aSecureBaseUrl",
        backDropSizes = (0 until 5).map { UUID.randomUUID().toString() },
        logoSizes = (0 until 5).map { UUID.randomUUID().toString() },
        posterSizes = (0 until 5).map { UUID.randomUUID().toString() },
        profileSizes = (0 until 5).map { UUID.randomUUID().toString() },
        stillSizes = (0 until 5).map { UUID.randomUUID().toString() }
    )

fun aShowDetailsNetwork(id: Int = Random().nextInt()) = ShowDetailsNetwork(
    id = id,
    name = "aName",
    backdropPath = "aBackdropPath",
    authors = (0 until 10).map { anAuthorNetwork() },
    runTimes = (0 until 10).map { it },
    firstAirDate = "aDate", // TODO change to date
    genres = (0 until 10).map { aGenreNetwork() },
    homePageUrl = "aHomePage",
    inProduction = Random().nextBoolean(),
    languages = (0 until 10).map { "Lang: $it" },
    lastEpisode = null,
    nextEpisode = null,
    networks = (0 until 10).map { aNetworkNetwork() },
    episodesCount = Random().nextInt(100),
    originCountries = (0 until 10).map { "Original country: $it" },
    originalName = "anOriginalName",
    description = "aDescription",
    popularity = Random().nextDouble(),
    posterPath = "aPosterPath",
    productionCompanies = (0 until 10).map { aProductionCompanyNetwork() },
    seasons = (0 until 10).map { aSeasonNetwork() },
    status = "aStatus",
    voteAverage = Random().nextDouble(),
    totalVotes = Random().nextInt(1000)
)

private fun anAuthorNetwork(id: Int = Random().nextInt()) = ShowAuthorNetwork(
    id = id,
    name = "aName",
    profileImage = "aProfileImageFor$id"
)

private fun aGenreNetwork(id: Int = Random().nextInt()) = GenreNetwork(
    id = id,
    name = "Name for genre: $id"
)

private fun aNetworkNetwork(id: Int = Random().nextInt()) = NetworkNetwork(
    id = id,
    name = "Name for network: $id",
    originCountry = "aCountry",
    logoPath = "aLogo"
)

private fun aProductionCompanyNetwork(id: Int = Random().nextInt()) = ProductionCompanyNetwork(
    id = id,
    logoPath = "LogoFor$id",
    originCountry = "anOriginalCountry",
    name = "Name for company $id"
)

private fun aSeasonNetwork(id: Int = Random().nextInt()) = SeasonNetwork(
    id = id,
    name = "Name of season $id",
    posterPath = "aPosterForSeason$id",
    description = "aDescription",
    episodesCount = Random().nextInt(100),
    seasonNumber = Random().nextInt(10),
    airDate = "anAirDate"
)