package net.byteabyte.mobiletv.core.tvshows.paged

import net.byteabyte.mobiletv.core.Repository
import net.byteabyte.mobiletv.core.tvshows.ShowId
import javax.inject.Inject

class GetSimilarShows @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(showId: ShowId, pageNumber: Int): GetPagedSummariesResult {
        return when (val result = repository.getSimilarShows(showId, pageNumber)) {
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