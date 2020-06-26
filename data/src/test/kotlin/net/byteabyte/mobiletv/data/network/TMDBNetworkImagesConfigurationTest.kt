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
import net.byteabyte.mobiletv.data.network.retrofit.TMDBApi
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class TMDBNetworkImagesConfigurationTest {

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
    fun `A 400 API response on configuration returns api error`() = runBlockingTest {
        val error = a400Error()
        whenever(tmdbApi.configuration(any())).thenThrow(error)

        val result = buildTmdbNetwork(tmdbApi).requestImagesConfiguration()

        assertEquals(TMDBNetworkResponse.ApiError(error), result)
    }

    @Test
    fun `A 401 API response on configuration returns unauthorized`() = runBlockingTest {
        val error = a401Error()
        whenever(tmdbApi.configuration(any())).thenThrow(error)

        val result = buildTmdbNetwork(tmdbApi).requestImagesConfiguration()

        assertEquals(TMDBNetworkResponse.Unauthorised, result)
    }

    @Test
    fun `A 500 API response on configuration returns server error`() = runBlockingTest {
        val error = a500Error()
        whenever(tmdbApi.configuration(any())).thenThrow(error)

        val result = buildTmdbNetwork(tmdbApi).requestImagesConfiguration()

        assertEquals(TMDBNetworkResponse.ServerError(error), result)
    }

    @Test
    fun `An unknown error on top rated in returns server error`() = runBlockingTest {
        val error = RuntimeException()
        whenever(tmdbApi.configuration(any())).thenThrow(error)

        val result = buildTmdbNetwork(tmdbApi).requestImagesConfiguration()

        assertEquals(TMDBNetworkResponse.ServerError(error), result)
    }

    @Test
    fun `An valid response returns the valid mapped response`() = runBlockingTest {
        val validResponse = aValidJsonConfigurationResult()
        whenever(tmdbApi.configuration(any())).thenReturn(validResponse)

        val result = (buildTmdbNetwork(tmdbApi).requestImagesConfiguration() as TMDBNetworkResponse.Success).data

        assertEquals(result.baseUrl, "aSecureBaseUrl")
        assertTrue(result.backDropSizes.isNotEmpty())
        assertTrue(result.posterSizes.isNotEmpty())
    }
}