package net.byteabyte.mobiletv.core.tvshows

import net.byteabyte.mobiletv.core.Repository
import javax.inject.Inject

class GetTopRated @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(pageNumber: Int): GetTopRatedResult {
        // TODO define the concrete errors that we want to return here. Single untyped now
        return when (val result = repository.getTopRated(pageNumber)) {
            is Repository.TopRatedResult.Success -> GetTopRatedResult.Success(
                TopRatedShowsPage(
                    currentPage = result.page,
                    previousPage = if (result.page == 1) null else result.page - 1,
                    nextPage = if (result.page == result.pagesCount) null else result.page + 1,
                    totalShowsCount = result.showsCount,
                    shows = result.results
                )
            )
            is Repository.TopRatedResult.Error -> GetTopRatedResult.Error
        }
    }

    sealed class GetTopRatedResult {
        object Error : GetTopRatedResult()
        data class Success(val page: TopRatedShowsPage) : GetTopRatedResult()
    }
}