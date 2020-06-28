package net.byteabyte.mobiletv.showdetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.byteabyte.mobiletv.core.tvshows.ShowId
import net.byteabyte.mobiletv.core.tvshows.details.GetShowDetails
import net.byteabyte.mobiletv.core.tvshows.details.ShowDetails

class ShowDetailsViewModel @ViewModelInject constructor(private val getShowDetails: GetShowDetails) :
    ViewModel() {

    private val _showDetails: MutableLiveData<ShowDetailsState> by lazy { MutableLiveData<ShowDetailsState>() }
    val showDetails: LiveData<ShowDetailsState> = _showDetails

    fun loadShow(showId: ShowId) {
        viewModelScope.launch {
            _showDetails.value = when (val showDetailsResult = getShowDetails(showId)) {
                GetShowDetails.GetShowDetailsResult.Error ->
                    ShowDetailsState.ShowDetailsLoadError
                is GetShowDetails.GetShowDetailsResult.Success ->
                    ShowDetailsState.ShowReady(showDetailsResult.showDetails)
            }
        }
    }

    sealed class ShowDetailsState {
        data class ShowReady(val showDetails: ShowDetails): ShowDetailsState()
        object ShowDetailsLoadError: ShowDetailsState()
    }
}