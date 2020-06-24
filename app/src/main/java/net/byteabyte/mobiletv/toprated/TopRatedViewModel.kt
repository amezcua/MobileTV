package net.byteabyte.mobiletv.toprated

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import net.byteabyte.mobiletv.core.tvshows.GetTopRated
import net.byteabyte.mobiletv.core.tvshows.Show

class TopRatedViewModel @ViewModelInject constructor(getTopRated: GetTopRated) : ViewModel() {
    private val _topRatedShows: LivePagedListBuilder<Int, Show> by dataSourceFactory {
        ShowsDataSourceFactory(viewModelScope, getTopRated)
    }
    val topRatedShows: LiveData<PagedList<Show>> by lazy { _topRatedShows.build() }
}