package net.byteabyte.mobiletv.core.tvshows.top_rated

import net.byteabyte.mobiletv.core.Repository
import javax.inject.Inject

class GetTopRated @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(pageNumber: Int): GetTopRatedResult {
        // TODO define the concrete errors that we want to return here. Single untyped now
        return when (val result = repository.getTopRated(pageNumber)) {
            is Repository.RepositoryResult.Success -> GetTopRatedResult.Success(
                TopRatedShowsPage(
                    currentPage = result.data.page,
                    previousPage = if (result.data.page == 1) null else result.data.page - 1,
                    nextPage = if (result.data.page == result.data.pagesCount) null else result.data.page + 1,
                    totalShowsCount = result.data.showsCount,
                    topRatedShows = result.data.results
                )
            )
            is Repository.RepositoryResult.Error -> GetTopRatedResult.Error
        }
    }

    sealed class GetTopRatedResult {
        object Error : GetTopRatedResult()
        data class Success(val page: TopRatedShowsPage) : GetTopRatedResult()
    }
}