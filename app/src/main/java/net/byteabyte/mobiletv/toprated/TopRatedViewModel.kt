package net.byteabyte.mobiletv.toprated

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.byteabyte.mobiletv.core.tvshows.GetTopRated
import net.byteabyte.mobiletv.core.tvshows.Show
import java.lang.Error

class TopRatedViewModel @ViewModelInject constructor(
    private val getTopRated: GetTopRated
) : ViewModel() {

    private val _topRatesShows: MutableLiveData<TopRatedShowsState> by lazy {
        MutableLiveData<TopRatedShowsState>()
    }
    val shows: LiveData<TopRatedShowsState> = _topRatesShows

    init {
        retrieveTopRatedShows()
    }

    private fun retrieveTopRatedShows() {
        viewModelScope.launch {
            _topRatesShows.value = when (val topRated = getTopRated()) {
                is GetTopRated.GetTopRatedResult.Error -> TopRatedShowsState.Error
                is GetTopRated.GetTopRatedResult.ResultPage -> TopRatedShowsState.ShowsAvailable(topRated.shows)
            }
        }
    }

    sealed class TopRatedShowsState {
        data class ShowsAvailable(val shows: List<Show>): TopRatedShowsState()
        object Error: TopRatedShowsState()
    }
}