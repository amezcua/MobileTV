package net.byteabyte.mobiletv.core.tvshows

import net.byteabyte.mobiletv.core.Repository
import javax.inject.Inject

class GetTopRated @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(): GetTopRatedResult {
        // TODO define the concrete errors that we want to return here. Single untyped now
        return when (val result = repository.getTopRated()) {
            is Repository.TopRatedResult.Success -> GetTopRatedResult.ResultPage(result.results)
            is Repository.TopRatedResult.Error -> GetTopRatedResult.Error
        }
    }

    sealed class GetTopRatedResult {
        object Error : GetTopRatedResult()
        data class ResultPage(val shows: List<Show>) : GetTopRatedResult()
    }
}