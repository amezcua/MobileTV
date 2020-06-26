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
import net.byteabyte.mobiletv.data.network.TMDBNetworkResponse.Success
import net.byteabyte.mobiletv.data.network.retrofit.InvalidApiResponseException
import net.byteabyte.mobiletv.data.network.retrofit.TMDBApi
import net.byteabyte.mobiletv.data.network.retrofit.top_rated.JsonTopRatedResponse
import net.byteabyte.mobiletv.data.network.retrofit.top_rated.JsonTopRatedShowsResponse
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

@ExperimentalCoroutinesApi
internal class TMDBNetworkTopRatedTest {
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
    fun `A 400 API response returns api error`() = runBlockingTest {
        val error = a400Error()
        whenever(tmdbApi.topRatedTvShows(any(), any())).thenThrow(error)

        val result = buildTmdbNetwork(tmdbApi).requestTopRated(1)

        assertEquals(TMDBNetworkResponse.ApiError(error), result)
    }

    @Test
    fun `A 401 API response returns unauthorized`() = runBlockingTest {
        val error = a401Error()
        whenever(tmdbApi.topRatedTvShows(any(), any())).thenThrow(error)

        val result = buildTmdbNetwork(tmdbApi).requestTopRated(1)

        assertEquals(TMDBNetworkResponse.Unauthorised, result)
    }

    @Test
    fun `A 500 API response returns server error`() = runBlockingTest {
        val error = a500Error()
        whenever(tmdbApi.topRatedTvShows(any(), any())).thenThrow(error)

        val result = buildTmdbNetwork(tmdbApi).requestTopRated(1)

        assertEquals(TMDBNetworkResponse.ServerError(error), result)
    }

    @Test
    fun `An unknown error returns server error`() = runBlockingTest {
        val error = RuntimeException()
        whenever(tmdbApi.topRatedTvShows(any(), any())).thenThrow(error)

        val result = buildTmdbNetwork(tmdbApi).requestTopRated(1)

        assertEquals(TMDBNetworkResponse.ServerError(error), result)
    }

    @Test
    fun `An invalid response returns invalid api error`() = runBlockingTest {
        whenever(
            tmdbApi.topRatedTvShows(
                any(),
                any()
            )
        ).thenReturn(anInvalidJsonTopRatedShowsResult())

        val result = buildTmdbNetwork(tmdbApi).requestTopRated(1)

        val error = (result as TMDBNetworkResponse.ServerError).error
        assertTrue(error is InvalidApiResponseException)
    }

    @Test
    fun `An valid response returns the valid mapped response`() = runBlockingTest {
        val validResponse = aValidJsonTopRatedShowsResult()
        whenever(tmdbApi.topRatedTvShows(any(), any())).thenReturn(validResponse)

        val responsePage = (buildTmdbNetwork(tmdbApi).requestTopRated(1) as Success).data

        assertEquals(responsePage.page, validResponse.page)
        assertEquals(responsePage.totalPages, validResponse.totalPages)
        assertEquals(responsePage.totalResults, validResponse.totalResults)
        assertEquals(responsePage.shows.size, validResponse.responses!!.size)
    }

    private fun anInvalidJsonTopRatedShowsResult() =
        JsonTopRatedShowsResponse(null, null, null, null)

    private fun aValidJsonTopRatedShowsResult() =
        JsonTopRatedShowsResponse(
            totalPages = Random().nextInt(10),
            totalResults = Random().nextInt(100),
            page = Random().nextInt(10),
            responses = aListOfJsonTopRatedResult()
        )

    private fun aListOfJsonTopRatedResult(maxResults: Int = 10) =
        (0 until maxResults).mapIndexed { index, _ ->
            JsonTopRatedResponse(
                id = index,
                name = "aResultName",
                overview = "The description",
                popularity = 0.0,
                voteAverage = 0.0,
                voteCount = 0,
                posterPath = "aPath",
                backdropPath = "backdropPath",
                originalLanguage = "en"
            )
        }
}