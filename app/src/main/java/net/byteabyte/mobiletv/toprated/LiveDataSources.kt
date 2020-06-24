package net.byteabyte.mobiletv.toprated

import android.util.Log
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import net.byteabyte.mobiletv.core.tvshows.GetTopRated
import net.byteabyte.mobiletv.core.tvshows.Show

internal inline fun <reified K, V> dataSourceFactory(
    crossinline factory: () -> DataSource.Factory<K, V>
): Lazy<LivePagedListBuilder<K, V>> =
    lazy {
        LivePagedListBuilder(factory.invoke(), TOP_RATED_PAGE_SIZE)
    }

internal class ShowsDataSourceFactory(
    private val scope: CoroutineScope,
    private val getTopRated: GetTopRated
) : DataSource.Factory<Int, Show>() {
    override fun create(): DataSource<Int, Show> = ShowsDataSource(scope, getTopRated)
}

private class ShowsDataSource(val scope: CoroutineScope, val getTopRated: GetTopRated) :
    PageKeyedDataSource<Int, Show>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Show>
    ) {
        scope.launch {
            val initialPage = 1
            when (val topRated = getTopRated(initialPage)) {
                is GetTopRated.GetTopRatedResult.Success -> callback.onResult(
                    topRated.page.shows,
                    null,
                    topRated.page.nextPage
                )
                else -> Log.d(TAG, "loadInitial. Got: $topRated")
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Show>) {
        Log.d(
            TAG,
            "loadAfter. Got: RequestedLoadSize: ${params.requestedLoadSize}, Key: ${params.key}"
        )
        scope.launch {
            when (val topRated = getTopRated(params.key)) {
                is GetTopRated.GetTopRatedResult.Success -> callback.onResult(
                    topRated.page.shows,
                    topRated.page.nextPage
                )
                else -> Log.d(TAG, "loadInitial. Got: $topRated")
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Show>) {
        Log.d(
            TAG,
            "loadBefore. Got: RequestedLoadSize: ${params.requestedLoadSize}, Key: ${params.key}"
        )
        scope.launch {
            when (val topRated = getTopRated(params.key)) {
                is GetTopRated.GetTopRatedResult.Success -> callback.onResult(
                    topRated.page.shows,
                    topRated.page.previousPage
                )
                else -> Log.d(TAG, "loadInitial. Got: $topRated")
            }
        }
    }
}

private const val TAG = "TopRatedViewModel"
private const val TOP_RATED_PAGE_SIZE = 20