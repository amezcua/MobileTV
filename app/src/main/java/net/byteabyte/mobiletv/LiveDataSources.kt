package net.byteabyte.mobiletv

import android.util.Log
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import net.byteabyte.mobiletv.core.tvshows.ShowId
import net.byteabyte.mobiletv.core.tvshows.paged.GetPagedSummariesResult
import net.byteabyte.mobiletv.core.tvshows.paged.GetSimilarShows
import net.byteabyte.mobiletv.core.tvshows.paged.GetTopRated
import net.byteabyte.mobiletv.core.tvshows.paged.ShowSummary

internal inline fun <reified K, V> dataSourceFactory(
    crossinline factory: () -> DataSource.Factory<K, V>
): Lazy<LivePagedListBuilder<K, V>> =
    lazy {
        LivePagedListBuilder(factory.invoke(), DEFAULT_PAGE_SIZE)
    }

internal class TopRatedShowsDataSourceFactory(
    private val scope: CoroutineScope,
    private val getTopRated: GetTopRated
) : DataSource.Factory<Int, ShowSummary>() {
    override fun create(): DataSource<Int, ShowSummary> =
        ShowSummariesDataSource(scope) { pageNumber: Int -> getTopRated(pageNumber) }
}

internal class SimilarShowsDataSourceFactory(
    private val scope: CoroutineScope,
    private val getSimilarShows: GetSimilarShows,
    private var showId: ShowId
) : DataSource.Factory<Int, ShowSummary>() {

    internal lateinit var dataSource: DataSource<Int, ShowSummary>

    override fun create(): DataSource<Int, ShowSummary> {
        dataSource = ShowSummariesDataSource(scope) { pageNumber: Int -> getSimilarShows(showId, pageNumber) }
        return dataSource
    }

    internal fun updateShow(showId: ShowId) {
        this.showId = showId
        dataSource.invalidate()
    }
}

private class ShowSummariesDataSource(
    val scope: CoroutineScope,
    val summariesRetriever: suspend (Int) -> GetPagedSummariesResult
) : PageKeyedDataSource<Int, ShowSummary>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ShowSummary>
    ) {
        scope.launch {
            val initialPage = 1
            when (val topRated = summariesRetriever(initialPage)) {
                is GetPagedSummariesResult.Success -> callback.onResult(
                    topRated.page.shows,
                    null,
                    topRated.page.nextPage
                )
                else -> Log.d(TAG, "loadInitial. Got: $topRated")
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ShowSummary>) {
        scope.launch {
            when (val topRated = summariesRetriever(params.key)) {
                is GetPagedSummariesResult.Success -> callback.onResult(
                    topRated.page.shows,
                    topRated.page.nextPage
                )
                else -> Log.d(TAG, "loadInitial. Got: $topRated")
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ShowSummary>) {
        scope.launch {
            when (val topRated = summariesRetriever(params.key)) {
                is GetPagedSummariesResult.Success -> callback.onResult(
                    topRated.page.shows,
                    topRated.page.previousPage
                )
                else -> Log.d(TAG, "loadInitial. Got: $topRated")
            }
        }
    }
}

private const val TAG = "ShowSummariesDataSource"
private const val DEFAULT_PAGE_SIZE = 20