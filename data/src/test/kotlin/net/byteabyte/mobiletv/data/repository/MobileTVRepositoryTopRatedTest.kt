package net.byteabyte.mobiletv.data.repository

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import net.byteabyte.mobiletv.core.Repository.RepositoryResult
import net.byteabyte.mobiletv.core.Repository.RepositoryResult.Error
import net.byteabyte.mobiletv.data.MobileTVRepository
import net.byteabyte.mobiletv.data.network.TMDBNetwork
import net.byteabyte.mobiletv.data.network.TMDBNetworkResponse
import net.byteabyte.mobiletv.data.network.TMDBNetworkResponse.Unauthorised
import net.byteabyte.mobiletv.data.network.aNetworkResponseShowsPage
import net.byteabyte.mobiletv.data.network.aValidImagesConfiguration
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class MobileTVRepositoryTopRatedTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val tmdbNetwork: TMDBNetwork = mock()

    @BeforeEach
    fun setup() = runBlockingTest {
        whenever(tmdbNetwork.requestImagesConfiguration()).thenReturn(
            TMDBNetworkResponse.Success(aValidImagesConfiguration())
        )
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `On configuration request unauthorised return authentication error`() = runBlockingTest {
        whenever(tmdbNetwork.requestImagesConfiguration()).thenReturn(Unauthorised)

        val topRated = buildRepository().getTopRated(1)

        assertEquals(Error.AuthenticationError, topRated)
    }

    @Test
    fun `On configuration request Api error return server error`() = runBlockingTest {
        val apiError = TMDBNetworkResponse.ApiError(RuntimeException("An api error"))
        whenever(tmdbNetwork.requestImagesConfiguration()).thenReturn(apiError)

        val topRated = buildRepository().getTopRated(1)

        assertEquals(Error.ServerError(apiError.error), topRated)
    }

    @Test
    fun `On configuration request Server error return server error`() = runBlockingTest {
        val apiError = TMDBNetworkResponse.ServerError(RuntimeException("A server error"))
        whenever(tmdbNetwork.requestImagesConfiguration()).thenReturn(apiError)

        val topRated = buildRepository().getTopRated(1)

        assertEquals(Error.ServerError(apiError.error), topRated)
    }

    @Test
    fun `On top rated unauthorised return authentication error`() = runBlockingTest {
        whenever(tmdbNetwork.requestTopRated(any())).thenReturn(Unauthorised)

        val topRated = buildRepository().getTopRated(1)

        assertEquals(Error.AuthenticationError, topRated)
    }

    @Test
    fun `On top rated api error return server error`() = runBlockingTest {
        val apiError = TMDBNetworkResponse.ApiError(RuntimeException("An api error"))
        whenever(tmdbNetwork.requestTopRated(any())).thenReturn(apiError)

        val topRated = buildRepository().getTopRated(1)

        assertEquals(Error.ServerError(apiError.error), topRated)
    }

    @Test
    fun `On top rated server error return server error`() = runBlockingTest {
        val apiError = TMDBNetworkResponse.ServerError(RuntimeException("An api error"))
        whenever(tmdbNetwork.requestTopRated(any())).thenReturn(apiError)

        val topRated = buildRepository().getTopRated(1)

        assertEquals(Error.ServerError(apiError.error), topRated)
    }

    @Test
    fun `On top rated success return the top rates with the images configured`() = runBlockingTest {
        val configuration = aValidImagesConfiguration()
        val topRatedPageResult = aNetworkResponseShowsPage()
        whenever(tmdbNetwork.requestImagesConfiguration()).thenReturn(TMDBNetworkResponse.Success(configuration))
        whenever(tmdbNetwork.requestTopRated(any())).thenReturn(TMDBNetworkResponse.Success(topRatedPageResult))

        val topRated = buildRepository().getTopRated(1) as RepositoryResult.Success

        assertEquals(topRatedPageResult.page, topRated.data.page)
        assertEquals(topRatedPageResult.totalPages, topRated.data.pagesCount)
        assertEquals(topRatedPageResult.totalResults, topRated.data.showsCount)
        assertEquals(topRatedPageResult.shows.size, topRated.data.results.size)
        topRated.data.results.forEach { show ->
            show.posterImages.forEach { posterImage ->
                val posterUrl = topRatedPageResult.shows.first { it.id == show.id }.posterImage
                assertEquals("${configuration.baseUrl}${posterImage.key}$posterUrl", posterImage.value)
            }

            show.backdropImages.forEach { backdropImage ->
                val backdropUrl = topRatedPageResult.shows.first { it.id == show.id }.backdropImage
                assertEquals("${configuration.baseUrl}${backdropImage.key}$backdropUrl", backdropImage.value)
            }
        }
    }

    private fun buildRepository(): MobileTVRepository =
        MobileTVRepository(tmdbNetwork)
}