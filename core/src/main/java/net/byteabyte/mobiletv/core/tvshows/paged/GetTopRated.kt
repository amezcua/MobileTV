package net.byteabyte.mobiletv.core.tvshows.paged

import net.byteabyte.mobiletv.core.Repository
import javax.inject.Inject

class GetTopRated @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(pageNumber: Int): GetPagedSummariesResult {
        return when (val result = repository.getTopRated(pageNumber)) {
            is Repository.RepositoryResult.Success -> GetPagedSummariesResult.Success(
                ShowSummariesPage(
                    currentPage = result.data.page,
                    previousPage = if (result.data.page == 1) null else result.data.page - 1,
                    nextPage = if (result.data.page == result.data.pagesCount) null else result.data.page + 1,
                    totalShowsCount = result.data.showsCount,
                    shows = result.data.results
                )
            )
            is Repository.RepositoryResult.Error -> GetPagedSummariesResult.Error
        }
    }
}