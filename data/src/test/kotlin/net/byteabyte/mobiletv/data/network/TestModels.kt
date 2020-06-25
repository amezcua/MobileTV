package net.byteabyte.mobiletv.data.network

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import net.byteabyte.mobiletv.data.network.imagesconfiguration.ImagesConfigurationNetwork
import net.byteabyte.mobiletv.data.network.retrofit.TMDBApi
import net.byteabyte.mobiletv.data.network.retrofit.configuration.JsonConfigurationResponse
import net.byteabyte.mobiletv.data.network.retrofit.configuration.JsonImagesConfiguration
import net.byteabyte.mobiletv.data.network.top_rated.TopRatedResponsePage
import net.byteabyte.mobiletv.data.network.top_rated.TopRatedTvShow
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

private fun aTopRatedShow(id: Int) = TopRatedTvShow(
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