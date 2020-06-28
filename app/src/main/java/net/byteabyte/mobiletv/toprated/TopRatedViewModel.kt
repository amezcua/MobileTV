package net.byteabyte.mobiletv.toprated

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import net.byteabyte.mobiletv.TopRatedShowsDataSourceFactory
import net.byteabyte.mobiletv.core.tvshows.paged.GetTopRated
import net.byteabyte.mobiletv.core.tvshows.paged.ShowSummary
import net.byteabyte.mobiletv.dataSourceFactory

class TopRatedViewModel @ViewModelInject constructor(getTopRated: GetTopRated) : ViewModel() {
    private val _topRatedShows: LivePagedListBuilder<Int, ShowSummary> by dataSourceFactory {
        TopRatedShowsDataSourceFactory(
            viewModelScope,
            getTopRated
        )
    }
    val topRatedShows: LiveData<PagedList<ShowSummary>> by lazy { _topRatedShows.build() }

    private val _showLoading by lazy {
        MediatorLiveData<Boolean>().apply {
            if (topRatedShows.value == null) this.value = true
            addSource(topRatedShows) {
                this.value = false
            }
        }
    }
    val showLoading: LiveData<Boolean> = _showLoading
}