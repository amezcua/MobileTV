package net.byteabyte.mobiletv.showdetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.launch
import net.byteabyte.mobiletv.SimilarShowsDataSourceFactory
import net.byteabyte.mobiletv.core.tvshows.ShowId
import net.byteabyte.mobiletv.core.tvshows.details.GetShowDetails
import net.byteabyte.mobiletv.core.tvshows.details.ShowDetails
import net.byteabyte.mobiletv.core.tvshows.paged.GetSimilarShows
import net.byteabyte.mobiletv.core.tvshows.paged.ShowSummary
import net.byteabyte.mobiletv.dataSourceFactory

class ShowDetailsViewModel @ViewModelInject constructor(
    private val getShowDetails: GetShowDetails,
    private val getSimilarShows: GetSimilarShows
) : ViewModel() {

    private var selectedShowId: ShowId? = null

    private val _showDetails: MutableLiveData<ShowDetailsState> by lazy { MutableLiveData<ShowDetailsState>() }
    val showDetails: LiveData<ShowDetailsState> = _showDetails

    private val similarShowsDataSourceFactory: SimilarShowsDataSourceFactory by lazy {
        SimilarShowsDataSourceFactory(
            viewModelScope,
            getSimilarShows,
            selectedShowId!!
        )
    }
    private val _similarShows: LivePagedListBuilder<Int, ShowSummary> by dataSourceFactory {
        similarShowsDataSourceFactory
    }
    val similarShows: LiveData<PagedList<ShowSummary>> by lazy { _similarShows.build() }

    fun loadShow(showId: ShowId) {
        selectedShowId = showId
        viewModelScope.launch {
            _showDetails.value = when (val showDetailsResult = getShowDetails(showId)) {
                GetShowDetails.GetShowDetailsResult.Error ->
                    ShowDetailsState.ShowDetailsLoadError
                is GetShowDetails.GetShowDetailsResult.Success -> {
                    similarShowsDataSourceFactory.updateShow(showDetailsResult.showDetails.id)
                    ShowDetailsState.ShowReady(showDetailsResult.showDetails)
                }
            }
        }
    }

    sealed class ShowDetailsState {
        data class ShowReady(val showDetails: ShowDetails): ShowDetailsState()
        object ShowDetailsLoadError: ShowDetailsState()
    }
}