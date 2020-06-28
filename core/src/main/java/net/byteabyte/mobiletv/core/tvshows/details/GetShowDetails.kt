package net.byteabyte.mobiletv.core.tvshows.details

import net.byteabyte.mobiletv.core.Repository
import net.byteabyte.mobiletv.core.tvshows.ShowId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetShowDetails @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(showId: ShowId): GetShowDetailsResult {
        println("Invoked: GetShowDetails $showId")
        return when (val result = repository.getShowDetails(showId)) {
            is Repository.RepositoryResult.Success -> GetShowDetailsResult.Success(result.data)
            is Repository.RepositoryResult.Error -> GetShowDetailsResult.Error
        }
    }

    sealed class GetShowDetailsResult {
        object Error : GetShowDetailsResult()
        data class Success(val showDetails: ShowDetails) : GetShowDetailsResult()
    }
}