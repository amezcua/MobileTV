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
import net.byteabyte.mobiletv.core.tvshows.ImagesMap
import net.byteabyte.mobiletv.core.tvshows.details.*
import net.byteabyte.mobiletv.data.MobileTVRepository
import net.byteabyte.mobiletv.data.network.TMDBNetwork
import net.byteabyte.mobiletv.data.network.TMDBNetworkResponse
import net.byteabyte.mobiletv.data.network.TMDBNetworkResponse.Unauthorised
import net.byteabyte.mobiletv.data.network.aShowDetailsNetwork
import net.byteabyte.mobiletv.data.network.aValidImagesConfiguration
import net.byteabyte.mobiletv.data.network.imagesconfiguration.ImagesConfigurationNetwork
import net.byteabyte.mobiletv.data.network.show_details.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class MobileTVRepositoryShowDetailsTest {

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

        val showDetails = buildRepository().getShowDetails(1)

        assertEquals(Error.AuthenticationError, showDetails)
    }

    @Test
    fun `On configuration request Api error return server error`() = runBlockingTest {
        val apiError = TMDBNetworkResponse.ApiError(RuntimeException("An api error"))
        whenever(tmdbNetwork.requestImagesConfiguration()).thenReturn(apiError)

        val showDetails = buildRepository().getShowDetails(1)

        assertEquals(Error.ServerError(apiError.error), showDetails)
    }

    @Test
    fun `On configuration request Server error return server error`() = runBlockingTest {
        val apiError = TMDBNetworkResponse.ServerError(RuntimeException("A server error"))
        whenever(tmdbNetwork.requestImagesConfiguration()).thenReturn(apiError)

        val showDetails = buildRepository().getShowDetails(1)

        assertEquals(Error.ServerError(apiError.error), showDetails)
    }

    @Test
    fun `On show details unauthorised return authentication error`() = runBlockingTest {
        whenever(tmdbNetwork.requestShowDetails(any())).thenReturn(Unauthorised)

        val showDetails = buildRepository().getShowDetails(1)

        assertEquals(Error.AuthenticationError, showDetails)
    }

    @Test
    fun `On show details api error return server error`() = runBlockingTest {
        val apiError = TMDBNetworkResponse.ApiError(RuntimeException("An api error"))
        whenever(tmdbNetwork.requestShowDetails(any())).thenReturn(apiError)

        val showDetails = buildRepository().getShowDetails(1)

        assertEquals(Error.ServerError(apiError.error), showDetails)
    }

    @Test
    fun `On show details server error return server error`() = runBlockingTest {
        val apiError = TMDBNetworkResponse.ServerError(RuntimeException("An api error"))
        whenever(tmdbNetwork.requestShowDetails(any())).thenReturn(apiError)

        val showDetails = buildRepository().getShowDetails(1)

        assertEquals(Error.ServerError(apiError.error), showDetails)
    }

    @Test
    fun `On show details success return the show details with the images`() = runBlockingTest {
        val configuration = aValidImagesConfiguration()
        val showDetailsNetwork = aShowDetailsNetwork()
        whenever(tmdbNetwork.requestImagesConfiguration()).thenReturn(
            TMDBNetworkResponse.Success(configuration)
        )
        whenever(tmdbNetwork.requestShowDetails(any())).thenReturn(
            TMDBNetworkResponse.Success(showDetailsNetwork)
        )

        val showDetails =
            (buildRepository().getShowDetails(1) as RepositoryResult.Success).data

        assertEquals(showDetailsNetwork.id, showDetails.id)
        assertEquals(showDetailsNetwork.name, showDetails.name)
        showDetails.backdropImages.forEach { image ->
            val url = showDetailsNetwork.backdropPath
            assertEquals("${configuration.baseUrl}${image.key}$url", image.value)
        }
        assertEquals(showDetailsNetwork.authors.size, showDetails.authors.size)
        assertAuthorsHaveImages(showDetails.authors, showDetailsNetwork.authors, configuration)
        assertEquals(showDetailsNetwork.firstAirDate, showDetails.firstAired)
        assertGenresLoaded(showDetailsNetwork.genres, showDetails.genres)
        assertEquals(showDetailsNetwork.homePageUrl, showDetails.homePageUrl)
        assertEquals(showDetailsNetwork.inProduction, showDetails.inProduction)
        assertEquals(showDetailsNetwork.languages, showDetails.languages)
        assertEpisodeMapped(showDetailsNetwork.lastEpisode, showDetails.lastEpisode, configuration)
        assertEpisodeMapped(showDetailsNetwork.nextEpisode, showDetails.nextEpisode, configuration)
        assertNetworksLoaded(showDetailsNetwork.networks, showDetails.networks, configuration)
        assertEquals(showDetailsNetwork.episodesCount, showDetails.episodesCount)
        assertEquals(showDetailsNetwork.originCountries, showDetails.originCountries)
        assertEquals(showDetailsNetwork.originalName, showDetails.originalName)
        assertEquals(showDetailsNetwork.description, showDetails.description)
        assertEquals(showDetailsNetwork.popularity, showDetails.popularity)
        assertPosterImages(showDetailsNetwork.posterPath, showDetails.posterImages, configuration)
        assertProductionCompaniesLoaded(showDetailsNetwork.productionCompanies, showDetails.productionCompanies, configuration)
        assertSeasonsLoaded(showDetailsNetwork.seasons, showDetails.seasons, configuration)
        assertEquals(showDetailsNetwork.status, showDetails.status)
        assertEquals(showDetailsNetwork.voteAverage, showDetails.voteAverage)
        assertEquals(showDetailsNetwork.totalVotes, showDetails.totalVotes)
    }

    private fun assertAuthorsHaveImages(
        authors: List<Author>,
        networkAuthors: List<ShowAuthorNetwork>,
        configuration: ImagesConfigurationNetwork
    ) {
        authors.forEach { author ->
            author.profileImages.forEach { profileImage ->
                val profileImageUrl = networkAuthors.first { it.id == author.id }.profileImage
                assertEquals(
                    "${configuration.baseUrl}${profileImage.key}$profileImageUrl",
                    profileImage.value
                )
            }
        }
    }

    private fun assertGenresLoaded(networkGenres: List<GenreNetwork>, genres: List<Genre>) {
        genres.forEach { genre ->
            val networkGenre = networkGenres.first { it.id == genre.id }
            assertEquals(networkGenre.id, genre.id)
            assertEquals(networkGenre.name, genre.name)
        }
    }

    private fun assertEpisodeMapped(episodeNetwork: EpisodeNetwork?, episode: Episode?, configuration: ImagesConfigurationNetwork) {
        if (episodeNetwork == null) assertNull(episode)
        assertEquals(episodeNetwork?.airDate, episode?.airDate)
        assertEquals(episodeNetwork?.description, episode?.description)
        assertEquals(episodeNetwork?.episodeNumber, episode?.episodeNumber)
        assertEquals(episodeNetwork?.name, episode?.name)
        assertEquals(episodeNetwork?.seasonNumber, episode?.seasonNumber)
        assertEquals(episodeNetwork?.voteAverage, episode?.voteAverage)
        assertEquals(episodeNetwork?.voteCount, episode?.votesCount)
        episode?.stillImages?.forEach { still ->
            assertEquals("${configuration.baseUrl}${still.key}${episodeNetwork?.stillPath}", still.value)
        }
    }

    private fun assertNetworksLoaded(
        networkNetworks: List<NetworkNetwork>,
        tvNetworks: List<TVNetwork>,
        configuration: ImagesConfigurationNetwork
    ) {
        tvNetworks.forEach { tvNetwork ->
            val networkNetwork = networkNetworks.first { it.id == tvNetwork.id }
            assertEquals(networkNetwork.id, tvNetwork.id)
            assertEquals(networkNetwork.name, tvNetwork.name)
            tvNetwork.logos.forEach { logo ->
                assertEquals("${configuration.baseUrl}${logo.key}${networkNetwork.logoPath}", logo.value)
            }
        }
    }

    private fun assertPosterImages(
        posterPath: String,
        posterImages: ImagesMap,
        configuration: ImagesConfigurationNetwork
    ) {
        posterImages.forEach { poster ->
            assertEquals("${configuration.baseUrl}${poster.key}${posterPath}", poster.value)
        }
    }

    private fun assertProductionCompaniesLoaded(
        productionCompaniesNetwork: List<ProductionCompanyNetwork>,
        productionCompanies: List<ProductionCompany>,
        configuration: ImagesConfigurationNetwork
    ) {
        productionCompanies.forEach { productionCompany ->
            val productionCompanyNetwork = productionCompaniesNetwork.first { it.id == productionCompany.id }
            assertEquals(productionCompanyNetwork.name, productionCompany.name)
            assertEquals(productionCompanyNetwork.originCountry, productionCompany.originCountry)

            productionCompany.logos.forEach { logo ->
                assertEquals("${configuration.baseUrl}${logo.key}${productionCompanyNetwork.logoPath}", logo.value)
            }
        }
    }

    private fun assertSeasonsLoaded(
        seasonsNetwork: List<SeasonNetwork>,
        seasons: List<Season>,
        configuration: ImagesConfigurationNetwork
    ) {
        seasons.forEach { season ->
            val seasonNetwork = seasonsNetwork.first { it.id == season.id }
            assertEquals(seasonNetwork.name, season.name)
            assertEquals(seasonNetwork.airDate, season.airDate)
            assertEquals(seasonNetwork.description, season.description)
            assertEquals(seasonNetwork.episodesCount, season.episodesCount)
            assertEquals(seasonNetwork.seasonNumber, season.seasonNumber)

            season.posterImages.forEach { poster ->
                assertEquals("${configuration.baseUrl}${poster.key}${seasonNetwork.posterPath}", poster.value)
            }
        }
    }

    private fun buildRepository(): MobileTVRepository =
        MobileTVRepository(tmdbNetwork)
}