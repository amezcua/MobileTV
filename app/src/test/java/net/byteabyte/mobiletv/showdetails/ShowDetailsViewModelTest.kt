package net.byteabyte.mobiletv.showdetails

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import net.byteabyte.bankstep.FakeLiveDataObserver
import net.byteabyte.bankstep.InstantTaskExecutorExtension
import net.byteabyte.mobiletv.core.tvshows.details.GetShowDetails
import net.byteabyte.mobiletv.core.tvshows.details.ShowDetails
import net.byteabyte.mobiletv.core.tvshows.paged.GetSimilarShows
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExperimentalCoroutinesApi
@ExtendWith(InstantTaskExecutorExtension::class)
internal class ShowDetailsViewModelTest {

    private val getDetailsUseCase: GetShowDetails = mock()
    private val getSimilarShowsUseCase: GetSimilarShows = mock()
    private lateinit var viewModel: ShowDetailsViewModel
    private val testDispatcher = TestCoroutineDispatcher()
    private val observer = FakeLiveDataObserver<ShowDetailsViewModel.ShowDetailsState>()

    @BeforeEach
    fun setup() = runBlockingTest {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        viewModel.showDetails.removeObserver(observer)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `Should emit error if we can not retrieve the show details`() = runBlockingTest {
        whenever(getDetailsUseCase(any())).thenReturn(GetShowDetails.GetShowDetailsResult.Error)
        viewModel = buildViewModel(getDetailsUseCase, getSimilarShowsUseCase)

        viewModel.loadShow(Random().nextInt())

        assertEquals(ShowDetailsViewModel.ShowDetailsState.ShowDetailsLoadError, observer.onlyObservedItem())
    }

    @Test
    fun `Should emit success with the show details when the show details can be retrieved`() = runBlockingTest {
        val showDetails: ShowDetails = mock()
        whenever(getDetailsUseCase(any())).thenReturn(GetShowDetails.GetShowDetailsResult.Success(showDetails))
        viewModel = buildViewModel(getDetailsUseCase, getSimilarShowsUseCase)

        viewModel.loadShow(Random().nextInt())

        assertEquals(ShowDetailsViewModel.ShowDetailsState.ShowReady(showDetails), observer.onlyObservedItem())
    }

    private fun buildViewModel(getShowDetails: GetShowDetails, getSimilarShows: GetSimilarShows) =
        ShowDetailsViewModel(getShowDetails, getSimilarShows).apply {
            showDetails.observeForever(observer)
        }
}