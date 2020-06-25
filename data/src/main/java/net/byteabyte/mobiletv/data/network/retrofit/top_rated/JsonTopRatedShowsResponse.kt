package net.byteabyte.mobiletv.data.network.retrofit.top_rated

import com.squareup.moshi.Json
import net.byteabyte.mobiletv.data.network.retrofit.InvalidApiResponseException
import net.byteabyte.mobiletv.data.network.top_rated.TopRatedResponsePage

data class JsonTopRatedShowsResponse(
    @Json(name = "total_pages") val totalPages: Int?,
    @Json(name = "total_results") val totalResults: Int?,
    @Json(name = "page") val page: Int?,
    @Json(name = "results") val responses: List<JsonTopRatedResponse>?
) {
    // The only validation that we are doing here are null checks. We could do more fancy validation
    // in the paging result.
    internal fun toNetworkResponsePage(): TopRatedResponsePage = try {
        TopRatedResponsePage(
            totalPages = this.totalPages!!,
            totalResults = this.totalResults!!,
            page = this.page!!,
            shows = responses!!.map { it.toTopRatedShow() }
        )
    } catch (e: Exception) {
        throw InvalidApiResponseException(e)
    }
}