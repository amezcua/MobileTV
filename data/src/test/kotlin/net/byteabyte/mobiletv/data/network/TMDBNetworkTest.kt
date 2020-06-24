package net.byteabyte.mobiletv.data.network

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import net.byteabyte.mobiletv.data.network.retrofit.InvalidApiResponseException
import net.byteabyte.mobiletv.data.network.retrofit.TMDBApi
import net.byteabyte.mobiletv.data.network.retrofit.top_rated.JsonTopRatedResult
import net.byteabyte.mobiletv.data.network.retrofit.top_rated.JsonTopRatedShowsResult
import net.byteabyte.mobiletv.data.network.top_rated.TopRatedNetworkResponse
import net.byteabyte.mobiletv.data.network.top_rated.TopRatedNetworkResponse.Success
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.HttpException
import java.util.*

@ExperimentalCoroutinesApi
internal class TMDBNetworkTest {
    private val testDispatcher = TestCoroutineDispatcher()
    private val tmdbApi: TMDBApi = mock()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `A 400 API response on top rated in returns api error`() = runBlockingTest {
        val error = a400Error()
        whenever(tmdbApi.topRatedTvShows(any())).thenThrow(error)

        val result = buildTmdbNetwork().requestTopRated()

        assertEquals(TopRatedNetworkResponse.ApiError(error), result)
    }

    @Test
    fun `A 401 API response on top rated in returns unauthorized`() = runBlockingTest {
        val error = a401Error()
        whenever(tmdbApi.topRatedTvShows(any())).thenThrow(error)

        val result = buildTmdbNetwork().requestTopRated()

        assertEquals(TopRatedNetworkResponse.Unauthorised, result)
    }

    @Test
    fun `A 500 API response on top rated in returns server error`() = runBlockingTest {
        val error = a500Error()
        whenever(tmdbApi.topRatedTvShows(any())).thenThrow(error)

        val result = buildTmdbNetwork().requestTopRated()

        assertEquals(TopRatedNetworkResponse.ServerError(error), result)
    }

    @Test
    fun `An unknown error on top rated in returns server error`() = runBlockingTest {
        val error = RuntimeException()
        whenever(tmdbApi.topRatedTvShows(any())).thenThrow(error)

        val result = buildTmdbNetwork().requestTopRated()

        assertEquals(TopRatedNetworkResponse.ServerError(error), result)
    }

    @Test
    fun `An invalid response returns invalid api error`() = runBlockingTest {
        whenever(tmdbApi.topRatedTvShows(any())).thenReturn(anInvalidJsonTopRatedShowsResult())

        val result = buildTmdbNetwork().requestTopRated()

        val error = (result as TopRatedNetworkResponse.ServerError).error
        assertTrue(error is InvalidApiResponseException)
    }

    @Test
    fun `An valid response returns the valid mapped response`() = runBlockingTest {
        val validResponse = aValidJsonTopRatedShowsResult()
        whenever(tmdbApi.topRatedTvShows(any())).thenReturn(validResponse)

        val result = buildTmdbNetwork().requestTopRated() as Success

        assertEquals(result.topRatedResponsePage.page, validResponse.page)
        assertEquals(result.topRatedResponsePage.totalPages, validResponse.totalPages)
        assertEquals(result.topRatedResponsePage.totalResults, validResponse.totalResults)
        assertEquals(result.topRatedResponsePage.shows.size, validResponse.results!!.size)
    }

    private fun a400Error(): HttpException = anHttpError(400)
    private fun a401Error(): HttpException = anHttpError(401)
    private fun a500Error(): HttpException = anHttpError(500)

    private fun anHttpError(code: Int): HttpException =
        mock<HttpException>().apply { whenever(this.code()).thenReturn(code) }

    private fun anInvalidJsonTopRatedShowsResult() =
        JsonTopRatedShowsResult(null, null, null, null)

    private fun aValidJsonTopRatedShowsResult() =
        JsonTopRatedShowsResult(
            totalPages = Random().nextInt(10),
            totalResults = Random().nextInt(100),
            page = Random().nextInt(10),
            results = aListOfJsonTopRatedResult(10)
        )

    private fun aListOfJsonTopRatedResult(maxResults: Int) =
        (0 until maxResults).mapIndexed { index, _ -> JsonTopRatedResult(
            id = index,
            name = "aResultName",
            overview = "The desciption",
            popularity = 0.0,
            voteAverage = 0.0,
            voteCount = 0,
            posterPath = "aPath",
            backdropPath = "backdropPath",
            originalLanguage = "en"
        ) }

    private fun buildTmdbNetwork(): TMDBNetwork = TMDBNetwork("anApiKey", tmdbApi)
}